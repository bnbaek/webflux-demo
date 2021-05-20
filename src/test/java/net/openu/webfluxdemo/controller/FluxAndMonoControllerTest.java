package net.openu.webfluxdemo.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebFluxTest
@AutoConfigureWebTestClient(timeout = "36000")
public class FluxAndMonoControllerTest {

  @Autowired
  WebTestClient webTestClient;

  //  @BeforeEach
//  public void setUp() {
//    webTestClient = webTestClient
//        .mutate()
//        .responseTimeout(Duration.ofMillis(30000))
//        .build();
//  }
  @Test
  public void flux_apporoach1() {
    Flux<Integer> integerFlux = webTestClient.get().uri("/flux")
        .accept(MediaType.APPLICATION_JSON_UTF8)
        .exchange()
        .expectStatus().isOk()
        .returnResult(Integer.class)
        .getResponseBody();

    StepVerifier.create(integerFlux)
        .expectSubscription()
        .expectNext(1)
        .expectNext(2)
        .expectNext(3)
        .expectNext(4)
        .expectNext(5)
        .verifyComplete();
  }

  @Test
  public void flux_apporoach2() {
    WebTestClient.ListBodySpec<Integer> integerListBodySpec = webTestClient.get().uri("/flux")
        .accept(MediaType.APPLICATION_JSON_UTF8)
        .exchange()
        .expectStatus().isOk()
        .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
        .expectBodyList(Integer.class)
        .hasSize(5);
  }

  @Test
  public void flux_apporoach3() {
    List<Integer> expectIntegers = Arrays.asList(1, 2, 3, 4, 5);
    EntityExchangeResult<List<Integer>> entityExchangeResult = webTestClient.get().uri("/flux")
        .accept(MediaType.APPLICATION_JSON_UTF8)
        .exchange()
        .expectStatus().isOk()
        .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
        .expectBodyList(Integer.class)
        .returnResult();

    assertEquals(expectIntegers, entityExchangeResult.getResponseBody());
  }

  @Test
  public void flux_apporoach4() {
    List<Integer> expectIntegers = Arrays.asList(1, 2, 3, 4, 5);
    webTestClient.get().uri("/flux")
        .accept(MediaType.APPLICATION_JSON_UTF8)
        .exchange()
        .expectStatus().isOk()
        .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
        .expectBodyList(Integer.class)
        .consumeWith(entity -> {
          assertEquals(expectIntegers, entity.getResponseBody());
        });

  }


  @Test
  public void flux_stream() {
    Flux<Long> longFluxStream = webTestClient.get().uri("/fluxstream")
        .accept(MediaType.APPLICATION_STREAM_JSON)
        .exchange()
        .expectStatus().isOk()
        .returnResult(Long.class)
        .getResponseBody();

    StepVerifier.create(longFluxStream)
        .expectSubscription()
        .expectNext(0l)
        .expectNext(1l)
        .expectNext(2l)
        .thenCancel()
        .verify();
  }

  @Test
  public void mono() {
    Integer expectedValue = 1;
    webTestClient.get().uri("/mono")
        .accept(MediaType.APPLICATION_JSON_UTF8)
        .exchange()
        .expectStatus().isOk()
        .expectBody(Integer.class)
        .consumeWith((response)->{
          assertEquals(expectedValue, response.getResponseBody());
        });

  }

}