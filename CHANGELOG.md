## 1.25 _(2015-08-10)_

* Make sure `Diffable.forFields[T]` is consistent with `T.equals` 

## 1.24 _(2015-08-10)_

* `containElements` has been dropped in favor of traversable diffables
* Improved formatting of traversable matchers

## 1.23 _(2015-07-16)_

* Improved readability of failure messages

## 1.22 _(2015-07-15)_

* Improved readability of failure messages

## 1.21 _(2015-07-13)_

* Improved readability of failure messages

## 1.20 _(2015-07-13)_

* Removed useless trait `Formatters`

## 1.19 _(2015-07-13)_

* Fix Diffable generation for polymorphic case classes (such as TupleN)

## 1.18 _(2015-07-13)_

* Add a `Diffable[Set[T]]`

## 1.17 _(2015-07-12)_

* Split matchete into multiple artefacts:

  - matchete-junit (depends on core)
  - matchete-json (depends on core)
  - matchete-xml (depends on core)
  - matchete-core (depends on macros)
  - matchete-macros

  People should therefore change their dependency toward `matchete-junit`.

## 1.16 _(2015-06-26)_

* Removed `MatcherComparator[Seq[T]]` in favor of `Diffable[Seq[T]]` as the latest is more composable 

## 1.15 _(2015-06-19)_

* Added JSON matchers:

  ```scala
  """{
    |  "firstName": "john",
    |  "lastName":"doe", "age":43
    |}
  """.stripMargin must equalJson(
  """{
    |  "nationality": "cowboy",
    |  "firstName": "john doe"
    |}
  """.stripMargin)
  ```

## 1.14 _(2015-06-19)_

* Fixed build configuration, the published matchete POM was incorrect and the macros weren't published.

## 1.13 _(2015-06-19)_

* Collection matchers now accept `TraversableOnce`, which make it possible to match against an `Iterator`

## 1.12 _(2015-06-18)_

* Added diffable support. Comparisons of case classes are now done on a field by field basis. 
  ```scala
    case class Person(name: String, age: Int, address: Address)
    case class Address(street: String)
  
    Person("john",12, Address("street")) must_== Person("john",12,Address("different street"))    
  ```
  Will throw the following `ComparisonFailure` (with `JunitMatchers`):
  ```
    org.junit.ComparisonFailure: Person(john,12,Address(street)) is not equal to Person(john,12,Address(different street))
    address.street = street ≠ address.street = different street 
    Expected :different street
    Actual   :street
  ```  

  Non case-classes are by default using standard equality but there's a facility for customization:
  ```scala
    class Person(val name: String, val age: Int, val address: Address)
    class Address(val street: String)
  
    implicit lazy val diffablePerson : Diffable[Person] = Diffable.forFields(_.name, _.age, _.address)
    implicit lazy val diffableAddress : Diffable[Address] = Diffable.forFields(_.street)

    new Person("john",12, new Address("street")) must_== new Person("john",12,new Address("different street"))
  ```
  Will throw the following `ComparisonFailure` (with `JunitMatchers`):
  ```
    org.junit.ComparisonFailure: org.backuity.matchete.XX$Person@46d56d67 is not equal to org.backuity.matchete.XX$Person@d8355a8
    address.street = street ≠ address.street = different street 
    Expected :different street
    Actual   :street
  ```  

## 1.11 _(2014-11-23)_

* Add `with` to exception matchers :
```scala
    parse() must throwA[ParsingError].`with`("a correct offset") { case ParsingError(msg,offset) => offset must_== 3 }
```
