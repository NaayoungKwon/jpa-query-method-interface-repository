package com.example.demo.common.parser;

import java.util.Collections;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Operator {

  EQUALS("Equals", "= ?"),
  BETWEEN("Between", "BETWEEN ? AND ?"),
  LESSTHAN("LessThan", "< ?"),
  LESSTHANEQUAL("LessThanEqual", "<= ?"),
  GREATERTHAN("GreaterThan", "> ?"),
  GREATERTHANEQUAL("GreaterThanEqual", ">= ?"),
  AFTER("After", "> ?"),
  BEFORE("Before", "< ?"),
  ISNULL("IsNull", "IS NULL"),
  NULL("Null", "IS NULL"),
  ISNOTNULL("IsNotNull", "IS NOT NULL"),
  NOTNULL("NotNull", "IS NOT NULL"),
  LIKE("Like", "LIKE ?"),
  NOTLIKE("NotLike", "NOT LIKE ?"),
  STARTINGWITH("StartingWith", "LIKE %%?"),
  ENDINGWITH("EndingWith", "LIKE ?%%"),
  CONTAINING("Containing", "LIKE %%?%%"),
  ORDERBY("OrderBy", "ORDER BY ? desc"),
  NOT("Not", "<> ?"),
  IN("In", "IN (?)"),
  NOTIN("NotIn", "NOT IN (?)"),
  TRUE("True", "= true"),
  FALSE("False", "= false"),
  IS("Is", "= ?"),
  ;

  private final String methodKeyword;
  private final String queryKeyword;

  private static final Set<String> keywords = Collections.unmodifiableSet(Stream.of(values()).map(Operator::getMethodKeyword).collect(Collectors.toSet()));
  public static final String OPERATOR_PATTERN = Stream.of(values()).map(Operator::getMethodKeyword).collect(Collectors.joining("|"));

  public static Operator find(String methodKeyword) {
    for (Operator operator : Operator.values()) {
      if (operator.getMethodKeyword().equals(methodKeyword)) {
        return operator;
      }
    }
    return EQUALS;
  }

}
