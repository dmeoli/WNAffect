# WordNet Affect [![Build Status](https://travis-ci.org/DonatoMeoli/WNAffect.svg?branch=master)](https://travis-ci.org/DonatoMeoli/WNAffect) [![Release](https://jitpack.io/v/DonatoMeoli/WNAffect.svg)](https://jitpack.io/#DonatoMeoli/WNAffect)

WNAffect allows to find the emotion of a given word using [WordNet Affect](http://wndomains.fbk.eu/wnaffect.html): an 
extension of [WordNet Domains](http://wndomains.fbk.eu/index.html), based on 
[Princeton's English WordNet 3.0](https://wordnet.princeton.edu/), which provides a set of emotional words organized in a tree:

```
     ┌physical-state
     ├behaviour
     ├trait
     ├sensation
     ├situation┐
     │         └emotion-eliciting-situation
     ├signal┐
     │      └edonic-signal
 root┤
     │            ┌cognitive-state
     │            ├cognitive-affective-state
     └mental-state┤
                  │               ┌mood
                  └affective-state┤
                                  │                              ┌emotionlessness
                                  │                       ┌apathy┤
                                  │                       │      └neutral-languor
                                  │       ┌neutral-emotion┤
                                  │       │               └neutral-unconcern┐
                                  │       │                                 │            ┌distance
                                  │       │                                 └indifference┤
                                  │       │                                              └withdrawal
                                  │       │                 ┌thing
                                  │       │                 ├pensiveness
                                  │       │                 ├gravity┐
                                  │       │                 │       └earnestness
                                  │       │                 ├ambiguous-fear┐
                                  │       │                 │              └reverence
                                  │       │                 │                     ┌ambiguous-hope
                                  │       │                 ├ambiguous-expectation┤
                                  │       │                 │                     └fever┐
                                  │       │                 │                           └buck-fever
                                  │       ├ambiguous-emotion┤
                                  │       │                 │                   
                                  │       │                 │                   ┌unrest
                                  │       │                 ├ambiguous-agitation┤
                                  │       │                 │                   │    
                                  │       │                 │                   └stir┐
                                  │       │                 │                        └electricity
                                  │       │                 └surprise┐
                                  │       │                          │            
                                  │       │                          │            ┌stupefaction
                                  │       │                          └astonishment┤
                                  │       │                                       └wonder┐
                                  │       │                                              └awe
                                  │       │                ┌gratitude┐
                                  │       │                │         └gratefulness
                                  │       │                ├levity┐
                                  │       │                │      └playfulness
                                  │       │                ├positive-fear┐
                                  │       │                │             └frisson
                                  │       │                ├fearlessness┐
                                  │       │                │            └security┐
                                  │       │                │                     └confidence
                                  │       │                ├positive-expectation┐
                                  │       │                │                    └anticipation┐
                                  │       │                │                                 └positive-suspense
                                  │       │                │          ┌self-esteem
                                  │       │                ├self-pride┤
                                  │       │                │          ├amour-propre
                                  │       │                │          └ego
                                  │       │                │         ┌attachment
                                  │       │                │         ├protectiveness
                                  │       │                ├affection┤
                                  │       │                │         ├soft-spot
                                  │       │                │         └regard
                                  │       │                │          
                                  │       │                │          ┌gusto
                                  │       │                ├enthusiasm┤
                                  │       │                │          └eagerness┐
                                  │       │                │                    └enthusiasm-ardor
                                  │       │                │             ┌hopefulness
                                  │       │                │             ├encouragement
                                  │       │                ├positive-hope┤
                                  │       │                │             └optimism┐
                                  │       │                │                      └sanguinity
                                  │       │                │        ┌placidity
                                  │       │                │        ├coolness
                                  │       │                ├calmness┤
                                  │       │                │        │            ┌peace
                                  │       │                │        └tranquillity┤
                                  │       │                │                     └easiness┐
                                  │       │                │                              └positive-languor
                                  │       │                │    ┌worship
                                  │       │                │    ├love-ardor
                                  │       │                │    ├amorousness
                                  │       │                │    ├puppy-love
                                  │       │                │    ├devotion
                                  │       │                ├love┤
                                  │       │                │    ├lovingness┐
                                  │       │                │    │          └warmheartedness
                                  │       │                │    ├benevolence┐
                                  │       │                │    │           └beneficence
                                  │       │                │    └loyalty
                                  │       ├positive-emotion┤
                                  │       │                │   ┌amusement
                                  │       │                │   ├exuberance
                                  │       │                │   ├happiness
                                  │       │                │   ├bonheur
                                  │       │                │   ├gladness
                                  │       │                │   ├rejoicing
                                  │       │                │   ├elation┐
                                  │       │                │   │       └euphoria
                                  │       │                │   ├exultation┐
                                  │       │                │   │          └triumph
                                  │       │                │   │            ┌bang
                                  │       │                │   ├exhilaration┤
                                  │       │                │   │            └titillation
                                  │       │                ├joy┤
                                  │       │                │   ├contentment┐
                                  │       │                │   │           │            ┌satisfaction-pride
                                  │       │                │   │           │            ├fulfillment
                                  │       │                │   │           └satisfaction┤
                                  │       │                │   │                        ├complacency┐
                                  │       │                │   │                        │           └smugness
                                  │       │                │   │                        └gloat
                                  │       │                │   │         ┌comfortableness
                                  │       │                │   ├belonging┤
                                  │       │                │   │         └closeness┐
                                  │       │                │   │                   └togetherness
                                  │       │                │   │         ┌hilarity
                                  │       │                │   ├merriment┤
                                  │       │                │   │         ├jollity
                                  │       │                │   │         └jocundity
                                  │       │                │   │            ┌buoyancy
                                  │       │                │   └cheerfulness┤
                                  │       │                │                └carefreeness
                                  │       │                │      ┌fondness
                                  │       │                │      ├captivation
                                  │       │                │      ├preference┐
                                  │       │                │      │          └weakness
                                  │       │                │      ├approval┐
                                  │       │                │      │        └favor
                                  │       │                │      ├admiration┐
                                  │       │                │      │          └hero-worship
                                  │       │                └liking┤
                                  │       │                       │        ┌kindheartedness
                                  │       │                       │        ├compatibility
                                  │       │                       ├sympathy┤
                                  │       │                       │        ├empathy┐
                                  │       │                       │        │       └identification
                                  │       │                       │        └positive-concern┐
                                  │       │                       │                         └softheartedness
                                  │       │                       │            ┌amicability
                                  │       │                       └friendliness┤
                                  │       │                                    ├brotherhood
                                  │       │                                    └good-will
                                  └emotion┤
                                          │                ┌ingratitude
                                          │                ├daze
                                          │                │        ┌self-depreciation
                                          │                ├humility┤
                                          │                │        └meekness
                                          │                │          ┌commiseration
                                          │                │          ├tenderness
                                          │                ├compassion┤
                                          │                │          └mercifulness┐
                                          │                │                       └forgiveness
                                          │                │       ┌hopelessness
                                          │                │       ├resignation┐
                                          │                │       │           └defeatism
                                          │                ├despair┤
                                          │                │       ├pessimism┐
                                          │                │       │         └cynicism
                                          │                │       └discouragement┐
                                          │                │                      └despair-intimidation
                                          │                │     ┌conscience
                                          │                │     ├self-disgust
                                          │                ├shame┤
                                          │                │     │             ┌self-consciousness
                                          │                │     │             ├shamefacedness
                                          │                │     │             ├chagrin
                                          │                │     └embarrassment┤
                                          │                │                   ├discomfiture
                                          │                │                   ├abashment
                                          │                │                   └confusion
                                          │                │       
                                          │                │       
                                          │                │       ┌distress
                                          │                │       ├negative-concern
                                          │                │       ├insecurity
                                          │                │       ├edginess
                                          │                │       ├sinking
                                          │                │       ├scruple
                                          │                ├anxiety┤
                                          │                │       │                  ┌stewing
                                          │                │       │                  ├tumult
                                          │                │       ├negative-agitation┤
                                          │                │       │                  └fidget┐
                                          │                │       │                         └impatience
                                          │                │       ├solicitude
                                          │                │       ├anxiousness
                                          │                │       ├angst
                                          │                │       └jitteriness
                                          │                │             ┌alarm
                                          │                │             ├creeps
                                          │                │             ├horror
                                          │                │             ├hysteria
                                          │                │             ├panic
                                          │                │             ├scare
                                          │                │             ├stage-fright
                                          │                │             ├fear-intimidation
                                          │                │             ├negative-unconcern┐
                                          │                │             │                  └heartlessness┐
                                          │                │             │                                └cruelty
                                          │                ├negative-fear┤
                                          │                │             │            ┌trepidation
                                          │                │             │            ├negative-suspense
                                          │                │             │            ├chill
                                          │                │             ├apprehension┤
                                          │                │             │            │          ┌shadow
                                          │                │             │            └foreboding┤
                                          │                │             │                       └presage
                                          │                │             │        ┌shyness
                                          │                │             └timidity┤
                                          │                │                      │          ┌hesitance
                                          │                │                      └diffidence┤
                                          │                │                                 └unassertiveness
                                          └negative-emotion┤
                                                           │                       ┌disinclination
                                                           │                       ├unfriendliness
                                                           │                       ├antipathy
                                                           │                       ├disapproval
                                                           │                       ├contempt
                                                           │               ┌dislike┤
                                                           │               │       │       ┌repugnance
                                                           │               │       ├disgust┤
                                                           │               │       │       └nausea
                                                           │               │       └alienation┐
                                                           │               │                  └isolation
                                                           ├general-dislike┤
                                                           │               │    ┌abhorrence
                                                           │               │    ├misanthropy
                                                           │               │    ├misogamy
                                                           │               │    ├misogyny
                                                           │               │    ├misology
                                                           │               │    ├misopedia
                                                           │               │    ├murderousness
                                                           │               │    ├despisal
                                                           │               │    ├misoneism┐
                                                           │               │    │         └misocainea
                                                           │               │    │           ┌maleficence
                                                           │               │    ├malevolence┤
                                                           │               │    │           ├vindictiveness
                                                           │               │    │           └malice
                                                           │               ├hate┤
                                                           │               │    │         ┌animosity
                                                           │               │    │         ├class-feeling
                                                           │               │    │         ├antagonism
                                                           │               │    │         ├aggression
                                                           │               │    │         ├belligerence┐
                                                           │               │    │         │            └warpath
                                                           │               │    └hostility┤
                                                           │               │              │          ┌heartburning
                                                           │               │              │          ├sulkiness
                                                           │               │              │          ├grudge
                                                           │               │              └resentment┤
                                                           │               │                         │    ┌covetousness
                                                           │               │                         └envy┤
                                                           │               │                              └jealousy
                                                           │               │     ┌infuriation
                                                           │               │     ├umbrage
                                                           │               │     ├huffiness
                                                           │               │     ├dander
                                                           │               │     ├indignation┐
                                                           │               │     │           └dudgeon
                                                           │               │     │    ┌wrath
                                                           │               │     ├fury┤
                                                           │               │     │    └lividity
                                                           │               └anger┤
                                                           │                     │         ┌pique
                                                           │                     │         ├frustration
                                                           │                     ├annoyance┤
                                                           │                     │         ├displeasure
                                                           │                     │         ├harassment
                                                           │                     │         └aggravation
                                                           │                     │          ┌irascibility
                                                           │                     └bad-temper┤
                                                           │                                └fit
                                                           │       ┌dolefulness
                                                           │       ├misery
                                                           │       ├forlornness
                                                           │       ├weepiness
                                                           │       ├downheartedness
                                                           │       ├cheerlessness┐
                                                           │       │             └joylessness
                                                           │       │          ┌gloom
                                                           │       ├melancholy┤
                                                           │       │          ├world-weariness
                                                           │       │          └heavyheartedness
                                                           └sadness┤
                                                                   │                    ┌attrition
                                                                   │      ┌regret-sorrow┤
                                                                   │      │             │           ┌guilt
                                                                   │      │             └compunction┤
                                                                   │      │                         └repentance
                                                                   ├sorrow┤
                                                                   │      │           ┌self-pity
                                                                   │      │           ├grief┐
                                                                   │      │           │     └dolor
                                                                   │      └lost-sorrow┤
                                                                   │                  │            ┌woe
                                                                   │                  └mournfulness┤
                                                                   │                               └plaintiveness
                                                                   │          ┌demoralization
                                                                   │          ├helplessness
                                                                   │          ├dysphoria
                                                                   └depression┤
                                                                              ├oppression┐
                                                                              │          └weight
                                                                              └despondency┐
                                                                                          └blue-devils
```
*(printed using [pptree](https://github.com/clemtoy/pptree))*

## Prerequisites

By default, requirement for compilation are:

 - JDK 8+
 - Maven

## Built with Maven

To create a jar file with dependencies including resource files:

```
$ mvn install assembly:single
```

## Using WNAffect

When using WS4J jar package from other projects add the [JitPack](https://jitpack.io/) repository to your POM file:

    <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>
    
and declare this github repo as a dependency:
    
    <dependencies>
        <dependency>
            <groupId>com.github.DonatoMeoli</groupId>
            <artifactId>WNAffect</artifactId>
            <version>x.y.z</version>
        </dependency>
    </dependencies>
    
## License [![License: GPL v3](https://img.shields.io/badge/License-GPL%20v3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)

This software is released under GNU GPL v3 License. See the [LICENSE](LICENSE) file for details.