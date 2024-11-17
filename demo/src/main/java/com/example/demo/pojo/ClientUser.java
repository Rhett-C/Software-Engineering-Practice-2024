package com.example.demo.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false) // AdminUser 与 ClientUser 不共用 ID
public class ClientUser extends User {

}
