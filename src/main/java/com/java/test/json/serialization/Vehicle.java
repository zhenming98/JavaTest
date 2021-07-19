package com.java.test.json.serialization;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

import java.util.List;

/**
 * @author yzm
 * @date 2021/4/25 - 17:10
 */
// Include Java class simple-name as JSON property "type"
@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS, include = JsonTypeInfo.As.PROPERTY, property = "type")
// Required for deserialization only
@JsonSubTypes({@JsonSubTypes.Type(Car.class), @JsonSubTypes.Type(Aeroplane.class)})
public abstract class Vehicle {
}

@Data
class Car extends Vehicle {
    public String licensePlate;
}

@Data
class Aeroplane extends Vehicle {
    public int wingSpan;
}

@Data
class PojoWithTypedObjects {
    public List<Vehicle> items;
}
