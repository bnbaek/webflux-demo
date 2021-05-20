package net.openu.webfluxdemo.document;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by iopenu@gmail.com on 2021/05/20
 * Github : https://github.com/bnbaek
 */
@DataMongoTest
@RunWith(SpringRunner.class)
public class ItemReactiveRepositoryTest {

  @Autowired
  ItemReactiveRepository itemReactiveRepository;

  List<Item> itemList = Arrays.asList(
      new Item(null, "samsung tv", 400.0  )
      , new Item(null, "lg tv", 200.0)
      , new Item(null, "apple watch", 199.0)
      , new Item(null, "beat headphones", 70.0)
  );
  @Before
  public void init(){
    itemReactiveRepository.deleteAll()
        .thenMany(Flux.fromIterable(itemList))
        .flatMap(itemReactiveRepository::save)
        .doOnNext(item -> System.out.println("insert items -> "+item))
    .blockLast();


  }
  @Test
  public void getAllItems(){
    StepVerifier.create(itemReactiveRepository.findAll())
        .expectSubscription()
        .expectNextCount(4 )
        .verifyComplete();
  }

}