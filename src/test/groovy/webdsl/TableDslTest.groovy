/**
 * Copyright to the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package webdsl

class TableDslTest extends AbstractNonServerTest {

  void test_table_as_objects() {
    html {
      table(id: 'table3') {
        tr { td('first name'); td('last name') }
        tr { td('pinky'); td('jones1') }
        tr { td('winky'); td('jones2') }
        tr { td('dinky'); td('jones3') }
        tr { td('linky'); td('jones4') }
        tr { td('stinky'); td('jones5') }
      }
    }

    webdsl {
      def expected = [
          [firstName: "pinky", lastName: "jones1"],
          [firstName: "winky", lastName: "jones2"],
          [firstName: "dinky", lastName: "jones3"],
          [firstName: "linky", lastName: "jones4"],
          [firstName: "stinky", lastName: "jones5"],
      ]
      assert table3.as.objects == expected
    }
  }

  void test_table_as_objects_with_names() {
    html {
      table(id: 'table3') {
        tr { td('first name'); td('last name') }
        tr { td('pinky'); td('jones1') }
        tr { td('winky'); td('jones2') }
        tr { td('dinky'); td('jones3') }
        tr { td('linky'); td('jones4') }
        tr { td('stinky'); td('jones5') }
      }
    }

    webdsl {
      def expected = [
          [first: "pinky", last: "jones1"],
          [first: "winky", last: "jones2"],
          [first: "dinky", last: "jones3"],
          [first: "linky", last: "jones4"],
          [first: "stinky", last: "jones5"],
      ]
      assert table3.as.objects('first', 'last') == expected
    }
  }

  void test_table_as_objects_rowRange() {
    html {
      table(id: 'table3') {
        tr { td('first name'); td('last name') }
        tr { td('pinky'); td('jones1') }
        tr { td('winky'); td('jones2') }
        tr { td('dinky'); td('jones3') }
        tr { td('linky'); td('jones4') }
        tr { td('stinky'); td('jones5') }
      }
    }

    webdsl {
      def expected = [
          [firstName: "dinky", lastName: "jones3"],
          [firstName: "linky", lastName: "jones4"],
      ]
      assert table3(rowRange: 2..3).as.objects == expected
    }
  }

  void test_table_as_objects_rowRange_through_end() {
    html {
      table(id: 'table3') {
        tr { td('first name'); td('last name') }
        tr { td('pinky'); td('jones1') }
        tr { td('winky'); td('jones2') }
        tr { td('dinky'); td('jones3') }
        tr { td('linky'); td('jones4') }
        tr { td('stinky'); td('jones5') }
      }
    }

    webdsl {
      def expected = [
          [firstName: "dinky", lastName: "jones3"],
          [firstName: "linky", lastName: "jones4"],
          [firstName: "stinky", lastName: "jones5"],
      ]
      assert table3(rowRange: 2..-1).as.objects == expected
    }
  }

  void test_table_as_objects_rowRange_from_end() {
    html {
      table(id: 'table3') {
        tr { td('first name'); td('last name') }
        tr { td('pinky'); td('jones1') }
        tr { td('winky'); td('jones2') }
        tr { td('dinky'); td('jones3') }
        tr { td('linky'); td('jones4') }
        tr { td('stinky'); td('jones5') }
      }
    }

    webdsl {
      def expected = [
          [firstName: "dinky", lastName: "jones3"],
          [firstName: "linky", lastName: "jones4"],
          [firstName: "stinky", lastName: "jones5"],
      ]
      assert table3(rowRange: -3..-1).as.objects == expected
    }
  }

  void test_table_as_objects_rowRange_of_one() {
    html {
      table(id: 'table3') {
        tr { td('first name'); td('last name') }
        tr { td('pinky'); td('jones1') }
        tr { td('winky'); td('jones2') }
        tr { td('dinky'); td('jones3') }
        tr { td('linky'); td('jones4') }
        tr { td('stinky'); td('jones5') }
      }
    }

    webdsl {
      def expected = [
          [firstName: "dinky", lastName: "jones3"],
      ]
      assert table3(rowRange: 2..-3).as.objects == expected
    }
  }

  void test_table_as_objects_column() {
    html {
      table(id: 'table3') {
        tr { td('first name'); td('last name') }
        tr { td('pinky'); td('jones1') }
        tr { td('winky'); td('jones2') }
        tr { td('dinky'); td('jones3') }
        tr { td('linky'); td('jones4') }
        tr { td('stinky'); td('jones5') }
      }
    }

    webdsl {
      def expected = [
          [lastName: "jones1"],
          [lastName: "jones2"],
          [lastName: "jones3"],
          [lastName: "jones4"],
          [lastName: "jones5"],
      ]
      assert table3(column: 1).as.objects == expected
    }
  }

  void test_table_as_objects_column_offset_and_row_range() {
    html {
      table(id: 'table3') {
        tr { td('first name'); td('last name') }
        tr { td('pinky'); td('jones1') }
        tr { td('winky'); td('jones2') }
        tr { td('dinky'); td('jones3') }
        tr { td('linky'); td('jones4') }
        tr { td('stinky'); td('jones5') }
      }
    }

    webdsl {
      def expected = [
          [lastName: "jones2"],
          [lastName: "jones3"],
          [lastName: "jones4"],
      ]
      assert table3(column: 1, rowRange: 1..3).as.objects == expected
    }
  }

  void test_table_as_list() {
    html {
      table(id: 'table3') {
        tr { td('first name'); td('last name') }
        tr { td('pinky'); td('jones1') }
        tr { td('winky'); td('jones2') }
        tr { td('dinky'); td('jones3') }
        tr { td('linky'); td('jones4') }
        tr { td('stinky'); td('jones5') }
      }
    }

    webdsl {
      def expected = [
          "first name",
          "pinky",
          "winky",
          "dinky",
          "linky",
          "stinky",
      ]
      assert table3.as.list == expected
    }
  }

  void test_table_as_list_with_offset() {
    html {
      table(id: 'table3') {
        tr { td('first name'); td('last name') }
        tr { td('pinky'); td('jones1') }
        tr { td('winky'); td('jones2') }
        tr { td('dinky'); td('jones3') }
        tr { td('linky'); td('jones4') }
        tr { td('stinky'); td('jones5') }
      }
    }

    webdsl {
      def expected = [
          "last name",
          "jones1",
          "jones2",
          "jones3",
          "jones4",
          "jones5",
      ]
      assert table3(column: 1).as.list == expected
    }
  }

  void test_table_as_list_with_row_range() {
    html {
      table(id: 'table3') {
        tr { td('first name'); td('last name') }
        tr { td('pinky'); td('jones1') }
        tr { td('winky'); td('jones2') }
        tr { td('dinky'); td('jones3') }
        tr { td('linky'); td('jones4') }
        tr { td('stinky'); td('jones5') }
      }
    }

    webdsl {
      def expected = [
          "pinky",
          "winky",
          "dinky",
      ]
      assert table3(rowRange: 1..3).as.list == expected
    }
  }

  void test_table_as_list_with_column_and_row_range() {
    html {
      table(id: 'table3') {
        tr { td('first name'); td('last name') }
        tr { td('pinky'); td('jones1') }
        tr { td('winky'); td('jones2') }
        tr { td('dinky'); td('jones3') }
        tr { td('linky'); td('jones4') }
        tr { td('stinky'); td('jones5') }
      }
    }

    webdsl {
      def expected = [
          "jones1",
          "jones2",
          "jones3",
      ]
      assert table3(column: 1, rowRange: 1..3).as.list == expected
    }
  }

  void test_table_as_columns() {
    html {
      table(id: 'table3') {
        tr { td('pinky'); td('jones1') }
        tr { td('winky'); td('jones2') }
        tr { td('dinky'); td('jones3') }
        tr { td('linky'); td('jones4') }
        tr { td('stinky'); td('jones5') }
      }
    }

    webdsl {
      def expected = [
          [first: "pinky", last: "jones1"],
          [first: "winky", last: "jones2"],
          [first: "dinky", last: "jones3"],
          [first: "linky", last: "jones4"],
          [first: "stinky", last: "jones5"],
      ]
      assert table3.as.columns('first', 'last') == expected
    }
  }

  void test_table_as_columns_not_all_columns_requested() {
    html {
      table(id: 'table3') {
        tr { td('pinky'); td('jones1') }
        tr { td('winky'); td('jones2') }
        tr { td('dinky'); td('jones3') }
      }
    }

    webdsl {
      def expected = [
          [first: "pinky"],
          [first: "winky"],
          [first: "dinky"],
      ]
      assert table3.as.columns('first') == expected
    }
  }

  void test_table_as_columns_extra_columns_requested() {
    html {
      table(id: 'table3') {
        tr { td('pinky'); td('jones1') }
        tr { td('winky'); td('jones2') }
        tr { td('dinky'); td('jones3') }
      }
    }

    webdsl {
      def expected = [
          [first: "pinky", last: "jones1", ssn:""],
          [first: "winky", last: "jones2", ssn:""],
          [first: "dinky", last: "jones3", ssn:""],
      ]
      assert table3.as.columns('first', 'last', 'ssn') == expected
    }
  }

  void test_table_as_columns_with_column_offset() {
    html {
      table(id: 'table3') {
        tr { td('pinky'); td('jones1') }
        tr { td('winky'); td('jones2') }
        tr { td('dinky'); td('jones3') }
        tr { td('linky'); td('jones4') }
        tr { td('stinky'); td('jones5') }
      }
    }

    webdsl {
      def expected = [
          [last: "jones1"],
          [last: "jones2"],
          [last: "jones3"],
          [last: "jones4"],
          [last: "jones5"],
      ]
      assert table3(column: 1).as.columns('last') == expected
    }
  }

  void test_table_as_columns_with_row_range() {
    html {
      table(id: 'table3') {
        tr { td('pinky'); td('jones1') }
        tr { td('winky'); td('jones2') }
        tr { td('dinky'); td('jones3') }
        tr { td('linky'); td('jones4') }
        tr { td('stinky'); td('jones5') }
      }
    }

    webdsl {
      def expected = [
          [first: "winky", last: "jones2"],
          [first: "dinky", last: "jones3"],
          [first: "linky", last: "jones4"],
      ]
      assert table3(rowRange: 1..3).as.columns('first', 'last') == expected
    }
  }

  void test_table_as_columns_with_column_offset_and_row_range() {
    html {
      table(id: 'table3') {
        tr { td('pinky'); td('jones1') }
        tr { td('winky'); td('jones2') }
        tr { td('dinky'); td('jones3') }
        tr { td('linky'); td('jones4') }
        tr { td('stinky'); td('jones5') }
      }
    }

    webdsl {
      def expected = [
          [last: "jones2"],
          [last: "jones3"],
          [last: "jones4"],
      ]
      assert table3(column: 1, rowRange: 1..3).as.columns('last') == expected
    }
  }

  void test_table_as_object() {
    html {
      table(id: 'person') {
        tr { td('first'); td('pinky') }
        tr { td('last'); td('jones') }
        tr { td('ssn'); td('111-11-1111') }
      }
    }

    webdsl {
      def expected = [
          first: 'pinky',
          last: 'jones',
          ssn: '111-11-1111'
      ]
      assert person.as.object == expected
    }
  }

  void test_table_as_object_with_rowRange() {
    html {
      table(id: 'person') {
        tr { td('first'); td('pinky') }
        tr { td('last'); td('jones') }
        tr { td('ssn'); td('111-11-1111') }
      }
    }

    webdsl {
      def expected = [
          last: 'jones',
          ssn: '111-11-1111'
      ]
      assert person(rowRange: 1..2).as.object == expected
    }
  }

  void test_table_as_object_with_column() {
    html {
      table(id: 'person') {
        tr { td('crap'); td('first'); td('pinky') }
        tr { td('crap'); td('last'); td('jones') }
        tr { td('crap'); td('ssn'); td('111-11-1111') }
      }
    }

    webdsl {
      def expected = [
          first: 'pinky',
          last: 'jones',
          ssn: '111-11-1111'
      ]
      assert person(column: 1).as.object == expected
    }
  }

  void test_table_process() {
    html {
      table(id: 'employees') {
        tr { td('First Name'); td('Last Name') }
        tr { td('pinky'); td('jones1') }
        tr { td('winky'); td('jones2') }
      }
    }

    webdsl {
      def result = []

      employees.process { row, column, td ->
        result << [rowIndex: row, columnIndex: column, content: td.textContent.trim()]
      }

      def expected = [
          [rowIndex: 0, columnIndex: 0, content: "First Name"],
          [rowIndex: 0, columnIndex: 1, content: "Last Name"],
          [rowIndex: 1, columnIndex: 0, content: "pinky"],
          [rowIndex: 1, columnIndex: 1, content: "jones1"],
          [rowIndex: 2, columnIndex: 0, content: "winky"],
          [rowIndex: 2, columnIndex: 1, content: "jones2"],
      ]

      assert result == expected
    }
  }

  void test_table_by_span() {
    html {
      table(id: 'employees') {
        tr {
          td { span(name: 'firstName', 'pinky') }; td { span(name: 'lastName', 'jones') }
        }
        tr {
          td { span(name: 'firstName', 'john') }; td { span(name: 'lastName', 'doe') }
        }
      }
    }

    webdsl {
      def expected = [[firstName: "pinky", lastName: "jones"], [firstName: "john", lastName: "doe"]]
      assert employees.by.span == expected
    }
  }
}