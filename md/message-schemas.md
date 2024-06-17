# Message Schemas

<iframe width="560" height="315" src="https://www.youtube.com/embed/2bPx3hfKX04"></iframe>

In this sandbox Schema Registry is already running, we are going to use it in some examples.
Schema Registry will allow you to create applications that share the same data structure when they communicate with each other.

This service could be key for your productions services, especially in a microservices ecosystems.
Although, you will find that evolving a schema could be a problem, particularly when
in your firsts stages of a new services.

It is important to know that Schema Registry is not a plugin or a kafka extension. It is a separated services running
in its own machine.

So, the concept Schema Evolution and Compatibility will become essential for you.
Check this out: [Schema Evolution and Compatibility](https://docs.confluent.io/platform/current/schema-registry/fundamentals/schema-evolution.html),
there you will find the **compatibility strategies** for multiple use cases.

Additionally, here you will find a lot of information about Schema Registry: [Getting Started with Schemas and Schema Registries](https://www.confluent.io/blog/schema-registry-for-beginners/).
