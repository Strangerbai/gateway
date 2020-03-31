package com.test.gateway.service.filter;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import com.test.gateway.service.service.RecordService;
import com.test.gateway.service.utils.RandomUtil;
import com.test.gateway.service.utils.ThreadLocalUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicReference;

import static org.springframework.cloud.gateway.filter.WebClientWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER;

public class ResponseGlobalFilterProcess implements GlobalFilter, Ordered {

    @Autowired
    RecordService recordService;

    protected static final Logger logger = LoggerFactory.getLogger(ResponseGlobalFilterProcess.class);

    public static final String charsetName = "ISO_8859_1";

    @Value("${skip_record}")
    String isSkip;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpResponse originalResponse = exchange.getResponse();
        DataBufferFactory bufferFactory = originalResponse.bufferFactory();
        String memo = RandomUtil.getStringWithNumber(12);
        if(Integer.valueOf(isSkip).compareTo(0)<0){
            ThreadLocalUtil.setFromIp(memo);
            recordService.saveRecord();
        }
        AtomicReference<String> bodyString = new AtomicReference<>(memo);
        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                if (body instanceof Flux) {
                    Flux<? extends DataBuffer> flux = (Flux<? extends DataBuffer>) body;
                    return super.writeWith(flux.buffer().map(dataBuffers -> {
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        dataBuffers.forEach(i -> {
                            byte[] array = new byte[i.readableByteCount()];
                            i.read(array);
                            DataBufferUtils.release(i);
                            try {
                                outputStream.write(array);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                        String str = outputStream.toString();
                        String memo = bodyString.get();
                        logger.info("响应体 {}", str);
                        try{
                            if(str.getBytes(charsetName).length<65535) {
                                recordService.updateRecord(str, memo);
                            }
                        } catch (Exception e){
                            logger.error("更新record数据出错");
                        }
//                        byte[] uppedContent = new String(content, Charset.forName(charsetName)).getBytes();
                        return bufferFactory.wrap(outputStream.toByteArray());
                    }));
                }
                // if body is not a flux. never got there.
                return super.writeWith(body);
            }
        };
        ThreadLocalUtil.clear();
        // replace response with decorator
        return chain.filter(exchange.mutate().response(decoratedResponse).build());

    }

    @Override
    public int getOrder() {
        return Integer.parseInt(isSkip);
    }
}
