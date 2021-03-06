package com.mountebank.javabank.http.predicates;

import com.google.common.collect.ImmutableMap;
import org.json.simple.JSONValue;
import org.junit.Test;

import static com.mountebank.javabank.http.predicates.PredicateType.EQUALS;
import static org.assertj.core.api.Assertions.assertThat;

public class PredicateTest {
    @Test
    public void shouldSetTheName() {
        Predicate predicate = new Predicate(PredicateType.EQUALS);

        assertThat(predicate.getType()).isEqualTo("equals");
    }

    @Test
    public void shouldSetTheDefaultNameToEquals() {
        Predicate equalsJsonMatcher = new Predicate(EQUALS);

        assertThat(equalsJsonMatcher.getType()).isEqualTo("equals");
    }

    @Test
    public void shouldSetThePath() {
        Predicate equalsJsonMatcher = new Predicate(EQUALS).withPath("/test");

        assertThat(equalsJsonMatcher.getPath()).isEqualTo("/test");
    }

    @Test
    public void shouldSetTheMethod() {
        Predicate equalsJsonMatcher = new Predicate(EQUALS).withMethod("POST");

        assertThat(equalsJsonMatcher.getMethod()).isEqualTo("POST");
    }

    @Test
    public void shouldSetTheQueryParameters() {
        Predicate equalsJsonMatcher = new Predicate(EQUALS).addQueryParameter("first", "1");

        assertThat(equalsJsonMatcher.getQueryParameter("first")).isEqualTo("1");
    }

    @Test
    public void shouldGetTheQueryParameters() {
        Predicate equalsJsonMatcher = new Predicate(EQUALS).addQueryParameter("first", "1").addQueryParameter("second", "2");

        assertThat(equalsJsonMatcher.getQueryParameters()).isEqualTo(ImmutableMap.of("first", "1", "second", "2"));
    }

    @Test
    public void shouldAddHeaders() {
        Predicate httpMatcher = new Predicate(EQUALS).addHeader("Accept", "text/plain").addHeader("Content-Type", "application/json");

        assertThat(httpMatcher.getHeaders()).isEqualTo(ImmutableMap.of("Accept", "text/plain", "Content-Type", "application/json"));
    }

    @Test
    public void shouldSetRequestFrom() {
        Predicate httpMatcher = new Predicate(EQUALS).withRequestFrom("127.0.0.1", "12345");

        assertThat(httpMatcher.getRequestFrom()).isEqualTo("127.0.0.1:12345");
    }

    @Test
    public void shouldSetBody() {
        Predicate httpMatcher = new Predicate(EQUALS).withBody("Hello World!");

        assertThat(httpMatcher.getBody()).isEqualTo("Hello World!");
    }

    @Test
    public void shouldSerializeToJSON() {
        Predicate equalsJsonMatcher = new Predicate(EQUALS)
                .withMethod("POST")
                .withPath("/testing")
                .withRequestFrom("127.0.0.1", "12345")
                .withBody("hello, world")
                .addQueryParameter("one", "1")
                .addQueryParameter("two", "2")
                .addHeader("Accept", "text/plain")
                .addHeader("Content-Type", "application/json");

        String expectedJson = "{\"equals\":{" +
                "\"path\":\"\\/testing\"," +
                "\"headers\":{\"Accept\":\"text\\/plain\",\"Content-Type\":\"application\\/json\"}," +
                "\"method\":\"POST\"," +
                "\"query\":{\"one\":\"1\",\"two\":\"2\"}," +
                "\"body\":\"hello, world\"," +
                "\"requestFrom\":\"127.0.0.1:12345\"}}";

        assertThat(JSONValue.parse(equalsJsonMatcher.toString())).isEqualTo(JSONValue.parse(expectedJson));
    }
}