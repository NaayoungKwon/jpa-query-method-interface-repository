package com.example.demo.common.parser;

import static com.example.demo.common.parser.Operator.OPERATOR_PATTERN;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class MethodParser implements Parser{

  public  String parse(String methodName, String tableName, Class<?>[] parameterTypes) {

    StringBuilder query = getDefaultSelectQuery(methodName, tableName);
    String whereClauses = getWhereClauses(methodName);

    for (Clause clause : splitParts(whereClauses)) {
      CombineKeyword keyword = clause.token();
      if (clause.isNotFirst()) {
        query.append(" ").append(keyword.name());
      }
      query.append(clause.toQueryString());
    }

    return convertToParameterType(query.toString(), parameterTypes);
  }

  private List<Clause> splitParts(String whereClause) {
    List<Clause> parts = new ArrayList<>();

    Pattern pattern = Pattern.compile("((?<token>(And|Or)?)(?<column>([A-Z][a-z]*)))(?<operator>(" + OPERATOR_PATTERN + ")?)");
    Matcher matcher = pattern.matcher(whereClause);

    while (matcher.find()) {
      CombineKeyword keyword = CombineKeyword.find(matcher.group("token"));
      String column = matcher.group("column");
      Operator operator = Operator.find(matcher.group("operator"));
      Clause clause = new Clause(keyword, operator, column);
      parts.add(clause);
    }

    return parts;
  }

  private StringBuilder getDefaultSelectQuery(String methodName, String tableName) {
    if(StringUtils.startsWithIgnoreCase(methodName, "findDistinctBy")){
      return new StringBuilder("SELECT DISTINCT * FROM " + tableName.toLowerCase() + " WHERE");
    }

    return new StringBuilder("SELECT * FROM " + tableName.toLowerCase() + " WHERE");
  }

  private String getWhereClauses(String method){
    // split to "By" keyword
    String whereSplitKeyword = "By";
    String[] split = method.split(whereSplitKeyword);
    if(split.length < 2){
      throw new RuntimeException("올바른 QueryMethod가 아닙니다. method : " + method);
    }

    return split[1];
  }

  private String convertToParameterType(String clause, Class<?>[] parameterTypes) {
    checkParameterCount(clause, parameterTypes);

    StringBuilder sb = new StringBuilder();
    int index = 0;
    for (String s : clause.split(" ")) {
      if(s.contains("?")){
        String newStr = s.replace("?", mapToFormat(parameterTypes[index++]));
        if(newStr.contains("%s")){
          sb.append("'").append(newStr).append("'").append(" ");
        } else {
          sb.append(newStr).append(" ");
        }
      } else {
        sb.append(s).append(" ");
      }
    }
    sb.deleteCharAt(sb.length()-1);
    return sb.toString();
  }

  private void checkParameterCount(String clause, Class<?>[] parameterTypes) {
    int parameterCount = clause.length() - clause.replace("?", "").length();
    if(parameterCount > 0 && parameterCount != parameterTypes.length) {
      throw new RuntimeException("parameter type count is not matched. clause : " + clause);
    }
  }

  private String mapToFormat(Class<?> clazz){
    if(clazz == String.class){
      return "%s";
    } else if(clazz == Integer.class){
      return "%d";
    } else if(clazz == Long.class){
      return "%d";
    } else if(clazz == Double.class){
      return "%f";
    } else if(clazz == Float.class){
      return "%f";
    } else if(clazz == Boolean.class){
      return "%b";
    } else if (clazz == List.class){
      return "%p"; // custom
    } else {
      return "%s";
    }
  }

}
