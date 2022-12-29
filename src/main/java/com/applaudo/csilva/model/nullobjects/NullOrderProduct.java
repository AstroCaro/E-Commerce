package com.applaudo.csilva.model.nullobjects;

import com.applaudo.csilva.model.OrderProduct;

public class NullOrderProduct extends OrderProduct {

    public static OrderProduct build() {
        return new NullOrderProduct();
    }

    public NullOrderProduct() {
        super(0,null, null, 0, 0.);
    }

    @Override
    public boolean isNull() {
        return true;
    }
}
