package com.sujal.LinkedInProject.APIGateway.filter;

import com.sujal.LinkedInProject.APIGateway.service.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.ws.rs.core.Request;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Component
@Slf4j

public class AuthenticationFilter extends AbstractGatewayFilterFactory {


    private final JwtService jwtService;

    public AuthenticationFilter(JwtService jwtService){
        super(Config.class);
        this.jwtService= jwtService;
    }


    @Override
    public GatewayFilter apply(Object config) {
        return (exchange,chain) ->{
            log.info("Auth request: {}",exchange.getRequest().getURI());

            final String tokenHeader= exchange.getRequest().getHeaders().getFirst("Authorization");

            if(tokenHeader== null || !tokenHeader.startsWith("Bearer")){
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            final String token = tokenHeader.split("Bearer ")[1];

            try {
                Long userIdLong = jwtService.getUserIdFromToken(token);
                String userId = userIdLong.toString();
                ServerHttpRequest mutatedRequest = exchange.getRequest()
                                .mutate()
                                        .header("X-User-Id",userId)
                                                .build();

                ServerWebExchange mutatedExchange = exchange.mutate()
                        .request(mutatedRequest)
                        .build();
//                ServerWebExchange mutatedExchange = exchange.mutate()
//                        .request(r -> r.header("X-User-Id",userId))
//                        .build();

                return chain.filter(mutatedExchange);
            }
            catch (JwtException e){
                log.error("Jwt Exception {}",e.getLocalizedMessage());
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

        };
    }
    static class Config{

    }
}
