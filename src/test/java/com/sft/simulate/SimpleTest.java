package com.sft.simulate;

import org.junit.Test;

import java.util.UUID;

public class SimpleTest {

    @Test
    public void tets1(){
        System.out.println(UUID.randomUUID().toString().replace("-",""));
    }

}
