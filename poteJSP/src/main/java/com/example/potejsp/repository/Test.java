package com.example.potejsp.repository;

import java.util.HashMap;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        VoterRepository voterRepository = new VoterRepository();
        HashMap<Integer, List<String>> map = voterRepository.getVoterListMap(14);
        for (Integer integer : map.keySet()) {
            System.out.println("key : " + integer );
            System.out.println("value : " + map.get(integer) );
        }
        System.out.println("test");
    }
}
