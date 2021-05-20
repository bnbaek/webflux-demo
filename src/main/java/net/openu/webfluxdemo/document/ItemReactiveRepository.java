package net.openu.webfluxdemo.document;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * Created by iopenu@gmail.com on 2021/05/20
 * Github : https://github.com/bnbaek
 */
public interface ItemReactiveRepository extends ReactiveMongoRepository<Item, String> {
}
