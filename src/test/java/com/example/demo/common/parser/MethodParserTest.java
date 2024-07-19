package com.example.demo.common.parser;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MethodParserTest {

  MethodParser parser;
  String tableName = "user";

  @BeforeEach
  void init() {
    parser = new MethodParser();
  }


  @Test
  @DisplayName("'And' 키워드 테스트")
  void testAndKeyword() {
    Class[] parameterTypes = new Class[]{Integer.class, String.class};

    String result = parser.parse("findByAgeAndName", tableName, parameterTypes);
    
    assertThat(result).isEqualTo("SELECT * FROM user WHERE age = %d AND name = '%s'");
  }

  @Test
  @DisplayName("'Or' 키워드 테스트")
  void testOrKeyword() {
    Class[] parameterTypes = new Class[]{Integer.class, String.class};

    String result = parser.parse("findByAgeOrName", tableName, parameterTypes);

    assertThat(result).isEqualTo("SELECT * FROM user WHERE age = %d OR name = '%s'");
  }

  @Test
  @DisplayName("'Between' 키워드 테스트")
  void testBetweenKeyword() {
    Class[] parameterTypes = new Class[]{LocalDate.class, LocalDate.class};

    String result = parser.parse("findByStartDateBetween", tableName, parameterTypes);

    assertThat(result).isEqualTo("SELECT * FROM user WHERE startDate BETWEEN '%s' AND '%s'");
  }

  @Test
  @DisplayName("'LessThan' 키워드 테스트")
  void testLessThanKeyword() {
    Class[] parameterTypes = new Class[]{Integer.class};

    String result = parser.parse("findByAgeLessThan", tableName, parameterTypes);

    assertThat(result).isEqualTo("SELECT * FROM user WHERE age < %d");
  }

  @Test
  @DisplayName("'GreaterThan' 키워드 테스트")
  void testGreaterThanKeyword() {
    Class[] parameterTypes = new Class[]{Integer.class};

    String result = parser.parse("findByAgeGreaterThan", tableName, parameterTypes);

    assertThat(result).isEqualTo("SELECT * FROM user WHERE age > %d");
  }

  @Test
  @DisplayName("'IsNull' 키워드 테스트")
  void testIsNullKeyword() {
    String result = parser.parse("findByAgeIsNull", tableName, null);

    assertThat(result).isEqualTo("SELECT * FROM user WHERE age IS NULL");
  }

  @Test
  @DisplayName("'Like' 키워드 테스트")
  void testLikeKeyword() {
    Class[] parameterTypes = new Class[]{String.class};

    String result = parser.parse("findByNameLike", tableName, parameterTypes);

    assertThat(result).isEqualTo("SELECT * FROM user WHERE name LIKE '%s'");
  }

  @Test
  @DisplayName("'StartingWith' 키워드 테스트")
  void testStartingWithKeyword() {
    Class[] parameterTypes = new Class[]{String.class};

    String result = parser.parse("findByNameStartingWith", tableName, parameterTypes);

    assertThat(result).isEqualTo("SELECT * FROM user WHERE name LIKE '\\%%s'");
  }

  @Test
  @DisplayName("'EndingWith' 키워드 테스트")
  void testEndingWithKeyword() {
    Class[] parameterTypes = new Class[]{String.class};

    String result = parser.parse("findByNameEndingWith", tableName, parameterTypes);
    
    assertThat(result).isEqualTo("SELECT * FROM user WHERE name LIKE '%s\\%'");
  }

  @Test
  @DisplayName("'In' 키워드 테스트")
  void testInKeyword() {
    Class[] parameterTypes = new Class[]{List.class};

    String result = parser.parse("findByAgeIn", tableName, parameterTypes);

    assertThat(result).isEqualTo("SELECT * FROM user WHERE age IN (%s)");
  }

  @Test
  @DisplayName("'True' 키워드 테스트")
  void testTrueKeyword() {
    String result = parser.parse("findByActiveTrue", tableName, null);
    assertThat(result).isEqualTo("SELECT * FROM user WHERE active = true");
  }

  @Test
  @DisplayName("'EndingWith'과 'GreaterThan' 키워드 조합 테스트")
  void testEndingWithAndGreaterThanKeyword() {
    Class[] parameterTypes = new Class[]{String.class, Integer.class};

    String result = parser.parse("findByNameEndingWithAndAgeIsGreaterThan", tableName, parameterTypes);

    assertThat(result).isEqualTo("SELECT * FROM user WHERE name LIKE '\\%%s' AND age > %d");
  }

  @Test
  @DisplayName("'Is Not Null'과 'True' 키워드 조합 테스트")
  void testInAndIsNullKeyword() {
    String result = parser.parse("findByNameIsNullAndActiveTrue", tableName, null);

    assertThat(result).isEqualTo("SELECT * FROM user WHERE name IS NOT NULL AND active = true");
  }

}
