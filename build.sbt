/*
 * Copyright 2016-2017 Nokia Solutions and Networks Oy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.typesafe.sbt.SbtScalariform.ScalariformKeys
import scalariform.formatter.preferences._

lazy val persistence = (project in file(".")).
  settings(name := "akka-typed-persistence").
  settings(commonSettings: _*).
  settings(publishSettings: _*)

lazy val examples = (project in file("examples")).
  settings(name := "akka-typed-persistence-examples").
  dependsOn(persistence).
  settings(commonSettings: _*).
  settings(publishArtifact := false)

lazy val commonSettings = Seq(

  // Scala:
  scalaVersion := "2.12.3",
  crossScalaVersions := Seq(scalaVersion.value, "2.11.11"),
  scalacOptions ++= Seq(
    "-feature",
    "-deprecation",
    "-unchecked",
    "-encoding", "UTF-8",
    "-target:jvm-1.8",
    "-Xlint:_",
    "-Xfuture",
    "-Ypartial-unification",
    "-Yno-adapted-args",
    "-Ywarn-numeric-widen",
    "-Ywarn-dead-code",
    "-Ywarn-unused-import"
  ),

  // first compile Java sources with regular
  // javac, then Scala souces with scalac
  // (we need this due to a hack in PublicActorAdapter.java)
  compileOrder := CompileOrder.JavaThenScala,

  // TODO: due to the same hack, scaladoc
  // doesn't work, so we disable it for now
  sources in (Compile, doc) := List(),

  // Static analysis:
  scalastyleFailOnError := true,
  wartremoverErrors ++= Seq(
    Wart.EitherProjectionPartial,
    Wart.OptionPartial,
    Wart.TraversableOps,
    Wart.JavaConversions,
    Wart.Return,
    Wart.Enumeration
  ),
  wartremoverWarnings ++= Seq(
    Wart.Product,
    Wart.Serializable,
    Wart.Option2Iterable,
    Wart.AsInstanceOf,
    Wart.IsInstanceOf,
    Wart.FinalCaseClass,
    Wart.TryPartial,
    Wart.Equals,
    Wart.Var,
    Wart.Null,
    Wart.MutableDataStructures,
    Wart.ExplicitImplicitTypes,
    Wart.ImplicitConversion,
    Wart.StringPlusAny
  ),

  // Code formatter:
  ScalariformKeys.preferences := ScalariformKeys.preferences.value
    .setPreference(PreserveSpaceBeforeArguments, true),

  // Dependencies:
  libraryDependencies ++= Seq(
    dependencies.akka.all,
    dependencies.cats,
    Seq(dependencies.shapeless),
    Seq(dependencies.scalatest, dependencies.scalacheck)
  ).flatten
)

lazy val publishSettings = Seq(
  organization := "com.nokia",
  version := "0.1.0-SNAPSHOT",
  publishMavenStyle := true,
  pomIncludeRepository := { repo => false }
)

lazy val dependencies = new {

  val cats = List(
    "org.typelevel" %% "cats-core" % "1.0.0-MF",
    "org.typelevel" %% "cats-free" % "1.0.0-MF",
    "org.typelevel" %% "cats-effect" % "0.4"
  )

  val shapeless = "com.chuusai" %% "shapeless" % "2.3.2"

  val scalatest = "org.scalatest" %% "scalatest" % "3.0.2" % "test-internal"
  val scalacheck = "org.scalacheck" %% "scalacheck" % "1.13.5" % "test-internal"

  val akka = new {

    val version = "2.5.4"
    val group = "com.typesafe.akka"

    val actor = group %% "akka-actor" % version
    val typed = group %% "akka-typed" % version
    val typedTestkit = group %% "akka-typed-testkit" % version
    val persistence = group %% "akka-persistence" % version

    val all = Seq(
      actor,
      typed,
      typedTestkit, // TODO: create separate -testkit module
      persistence
    )
  }
}

// For CI et al.:
addCommandAlias("staticAnalysis", ";test:compile;scalastyle;examples/test:compile;examples/scalastyle") // additional tools can be added here
addCommandAlias("testAll", ";test;examples/test")
addCommandAlias("validate", ";staticAnalysis;testAll")
addCommandAlias("measureCoverage", ";clean;coverage;test;coverageReport;coverageOff")
