package funsets

import org.scalatest.FunSuite


import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  // test("string take") {
  //   val message = "hello, world"
  //   assert(message.take(5) == "hello")
  // }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
//   test("adding ints") {
//     assert(1 + 3 === 3)
//   }


  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val s4 = singletonSet(4)
    val s5 = singletonSet(5)
    val s6 = singletonSet(6)
    val s7 = singletonSet(7)
    val under10 : FunSets.Set = (x :Int) => x < 10
    val su = union(union(s1, s2), s3)
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("union contains all elements of each set") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  test("intersect contains all elements in common each set") {
    new TestSets {
      val s = union(s1, s2)
      val si = intersect(s1, under10)
      assert(contains(si, 1), "Intersect 1")
      assert(contains(under10, 1), "Intersect 1")
      assert(!contains(si, 2), "Intersect 2")
    }
  }

  test("diff contains all elements not in common each set") {
    new TestSets {
      val su0 = union(s1, s2)
      val su2 = union(s1, s3)
      val s = diff(su0, su2)
      assert(!contains(s, 1), "Diff 1")
      assert(contains(s, 2), "Diff 1")
      assert(contains(s, 3), "Diff 2")
    }
  }


  test("filter contains all elements that obey a predicate") {
    new TestSets {
      val s = filter(under10, (x: Int) => x < 5)
      assert(contains(s, 1), "filter 1")
      assert(contains(s, 2), "filter 2")
      assert(contains(s, 3), "filter 3")
      assert(contains(s, 4), "filter 4")
      assert(!contains(s, 5), "filter 5")
    }
  }


  test("forall checks if all elements in a set satisfy a predicate") {
    new TestSets {
      assert(!forall(under10, (x: Int) => x < 5), "forall 1")
      assert(forall(under10, (x: Int) => x < 20), "forall 2")
    }
  }

  test("exists checks if any elements in a set satisfies a predicate") {
    new TestSets {
      assert(exists(under10, (x: Int) => x > 8), "exists 1")
      assert(!exists(under10, (x: Int) => x > 20), "exists 2")
    }
  }


  test("map applies a function f to all elements of a set and returns the transformed set") {
    new TestSets {
      val sm = map(under10, x => x * 2)
      assert(contains(sm, 18), "map1")
      assert(!contains(sm, 1), "map2")
    }
  }
}
