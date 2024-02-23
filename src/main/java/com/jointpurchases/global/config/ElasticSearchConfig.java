package com.jointpurchases.global.config;

import co.elastic.clients.transport.TransportUtils;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import javax.net.ssl.SSLContext;

@Configuration
@EnableElasticsearchRepositories
public class ElasticSearchConfig extends ElasticsearchConfiguration {

    @Value("${spring.elasticsearch.uris}")
    private String host;

    @Value("${spring.elasticsearch.username}")
    private String username;

    @Value("${spring.elasticsearch.password}")
    private String password;

    @Value("${spring.elasticsearch.fingerprint}")
    private String fingerprint;

    @NonNull
    @Override
    public ClientConfiguration clientConfiguration() {
        SSLContext sslContext = TransportUtils.sslContextFromCaFingerprint(fingerprint);

        return ClientConfiguration.builder()
                .connectedTo(host)
//                .usingSsl(sslContext)
//                .withBasicAuth(username, password)
                .build();
    }

}