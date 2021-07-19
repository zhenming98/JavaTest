package com.java.test.json.serialization;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.java.test.json.serialization.animal.Animal;
import com.java.test.json.serialization.animal.Cat;
import com.java.test.json.serialization.animal.Dog;
import lombok.Data;

/**
 * @author yzm
 * @date 2021/4/25 - 16:21
 */
@Data

public class Zoo {

    private String id;

    private String type;


    private Animal animal;

}
