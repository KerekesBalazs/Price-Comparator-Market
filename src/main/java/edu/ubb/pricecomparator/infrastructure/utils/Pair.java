package edu.ubb.pricecomparator.infrastructure.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Pair<A, B> {
    public A first;
    public B second;
}