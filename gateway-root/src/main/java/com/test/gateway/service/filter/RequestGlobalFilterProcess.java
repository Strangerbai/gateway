package com.test.gateway.service.filter;

import com.alibaba.fastjson.JSON;
import com.test.gateway.service.utils.ThreadLocalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static org.springframework.cloud.gateway.filter.WebClientWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER;

public class RequestGlobalFilterProcess implements GlobalFilter, Ordered {

    protected static final Logger logger = LoggerFactory.getLogger(RequestGlobalFilterProcess.class);

    @Value("${skip_record}")
    String isSkip;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        String method = request.getMethodValue();
        ThreadLocalUtil.setRequestMethod(method);
        ThreadLocalUtil.setUri(request.getURI().toString());
        ThreadLocalUtil.setRequestHeader(request.getHeaders().toString());
        String contentType = request.getHeaders().getFirst("Content-Type");
        AtomicReference<String> bodyString = new AtomicReference<>("");
        logger.info("contentType: {}", contentType);
        if ("POST".equals(method) && Integer.valueOf(isSkip).compareTo(0)<0){
            return DataBufferUtils.join(exchange.getRequest().getBody())
                    .flatMap(dataBuffer -> {
                        byte[] bytes = new byte[dataBuffer.readableByteCount()];
                        dataBuffer.read(bytes);
                        try {
                            bodyString.set(new String(bytes, "ISO_8859_1"));
                            logger.info("请求体: {}", bodyString.get());
                            ThreadLocalUtil.setRequestContent(bodyString.get());
                            exchange.getAttributes().put("POST_BODY",bodyString.get());
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        DataBufferUtils.release(dataBuffer);
                        Flux<DataBuffer> cachedFlux = Flux.defer(() -> {
                            DataBuffer buffer = exchange.getResponse().bufferFactory()
                                    .wrap(bytes);
                            return Mono.just(buffer);
                        });

                        ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(
                                exchange.getRequest()) {
                            @Override
                            public Flux<DataBuffer> getBody() {
                                return cachedFlux;
                            }
                        };
                        return chain.filter(exchange.mutate().request(mutatedRequest)
                                .build());
                    });
        }else if ("GET".equals(method)) {
            Map requestQueryParams = request.getQueryParams();
            String getBodyString = JSON.toJSON(requestQueryParams).toString();
            logger.info("get 请求体：{}", JSON.toJSON(requestQueryParams));
            ThreadLocalUtil.setRequestContent(getBodyString);
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return WRITE_RESPONSE_FILTER_ORDER - 100;
    }

}
