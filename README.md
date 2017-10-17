# Nearby

### Development Notes & Design Decisions

The purpose of this document is to explain some of the design decisions that went into developing this application and also answer questions that may have entered your mind after going through the code.

#### 1. Overall Architecture

This application has been developed using MVVM architecture with data-binding as described [here](https://github.com/googlesamples/android-architecture/tree/todo-mvvm-databinding/).

The main guidelines I followed were:
- The View must never interact with the models directly. It should only interact with the ViewModels.
- The ViewModels queries the Model layer and updates the View. It also handles events on the view.
- The ViewModel exposes observable properties which the View binds and responds to
- When a parent view has child views (e.g. list items in a fragment), the parent view model is responsible for creating the child view model.


#### 2. Immutable Model Classes

Immutable classes are discussed at great lengths in `Effective Java`, Item 15.

In the context of this application, and mobile applications in general, it makes sense to make the model classes immutable.

For the most part, mobile applications fetch data from an API and display it to the user. This data (for example, Place data), is not meant to be modified so providing setters on the model classes is introducing the possibility of errors.

If we had a feature where we wished to allow the user to modify the data e.g. User Profile, it would make sense to have an immutable `Profile` model to display the current state of the profile and a mutable `Profile.Builder` model to reflect the edits that the user wishes to make.

The downside of using immutable classes is that you end up with constructors that take many arguments.

#### 3. Preconditions

In several places, the application sanitizes method inputs using `Preconditions`. If the conditions fail, the application will crash. 

This style of development is known as [`Fail Fast`](https://en.wikipedia.org/wiki/Fail-fast) and is described in Effective Java item 58.

The main benefits are that this allows any invalid states that arise in the applications to be detected during the Development and Testing stage.

Additionally, as the goal is to throw the exception at the first point that an invalid state occurs, it becomes easy to identify which part of the code produces the invalid state. Otherwise, allowing the application to continue with an invalid state could result in side-effects and it would take a long time to trace back the side-effects to the original error.

#### 4. Dagger 2, Why use a custom `@ApplicationScope` instead of the `@Singleton` scope.

`@ApplicationScope` and `@Singleton` scope both serve the exact same purpose.

The problem with the `@Singleton` scope is that the name is misleading. Let's say you have a component that has a `ThingModule`. `ThingModule` provides a `@Singleton Thing`.


```java
@Module
public class ThingModule{
    @Singleton
    @Provides
    public Thing thing(){
       return new Thing();
    }
}
```

If you were to build two instances of the same Component, you would get two different instances of `Thing` (not a single instance as the annotation would suggest).

`@ApplicationScope` does not fix this problem but it does not mislead you about the nature of the instance that is returned; that's why I prefer to use it.

