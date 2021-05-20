package net.openu.webfluxdemo.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by iopenu@gmail.com on 2021/05/20
 * Github : https://github.com/bnbaek
 */

@Document //@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
  @Id
  private String id;
  private String description;
  private Double price;

}
