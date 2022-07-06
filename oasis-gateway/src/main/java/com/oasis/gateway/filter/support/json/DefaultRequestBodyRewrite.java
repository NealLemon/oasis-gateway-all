package com.oasis.gateway.filter.support.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.factory.rewrite.RewriteFunction;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @ClassName RequestBodyRewrite
 * @Description TODO
 * @Author zhushaobin
 * @Date 2022/7/6 21:00
 */
public class DefaultRequestBodyRewrite implements RewriteFunction<JsonNode, JsonNode> {

    private final JsonSchema validateSchema;

    public DefaultRequestBodyRewrite(JsonSchema validateSchema) {
        this.validateSchema = validateSchema;
    }

    @Override
    public Publisher<JsonNode> apply(ServerWebExchange serverWebExchange, JsonNode originalBody) {
        ProcessingReport report =  validateSchema.validateUnchecked(originalBody);
        if(report.isSuccess()) {
            return Mono.just(originalBody);
        }
        return Mono.error(new Exception(report.iterator().next().getMessage()));
    }
}
