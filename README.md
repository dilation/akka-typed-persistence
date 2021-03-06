<!--

   Copyright 2016-2018 Nokia Solutions and Networks Oy

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 -->

# Akka Typed Persistence

[![Gitter chat](https://badges.gitter.im/nokia/akka-typed-persistence.png)](https://gitter.im/nokia/akka-typed-persistence "Gitter chat")

**Event sourcing for Akka Typed**

This library implements actor persistence for
[Akka Typed](http://doc.akka.io/docs/akka/2.5.4/scala/typed.html)
with event sourcing. It provides:

* an actor definition API, which
    * is integrated with Akka Typed,
    * statically _type safe_,
    * and _supports actor persistence_ (with event sourcing and snapshots);
* and an _implementation_ of this API
    * based on `akka-typed` and `akka-persistence`.

A conference talk introducing the library was presented at
[Scala By the Bay](http://sched.co/7iUT) ([slides](https://t.co/IsENEuxShc)).
For the full code of the example in the talk, see
[this file](examples/src/main/scala/com/example/PingActor.scala).


## Motivation

[Akka Typed](http://doc.akka.io/docs/akka/2.5.4/scala/typed.html)
provides a type safe API for defining Akka actors. However, originally
it had no solution for actor persistence. The goal of this library was
exactly that: integrating Akka Typed and Akka Persistence. (Since then,
Akka Typed have been extended to include a
[persistence API](https://akka.io/blog/2017/10/13/typed-persistence).)


## Getting started

This library is currently not published, but you can use it by
depending on this git repository in sbt:

```scala
dependsOn(ProjectRef(uri("https://github.com/nokia/akka-typed-persistence.git#master"), "persistence"))
```

For how to use the library, see
[this example](examples/src/main/scala/com/example/PingActor.scala).

**Dependencies:**

* Scala 2.11 or 2.12
* Akka 2.5.4
* Cats 1.0.0-MF, cats-effect 0.4 and shapeless 2.3.2
