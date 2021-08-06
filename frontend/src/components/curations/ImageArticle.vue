<template>
    <article class="ui-article" :data-key="dataKey">
        <h2 class="ui-heading">{{title}}</h2>
        <p class="ui-paragraph">{{description}}</p>
    </article>
</template>

<script>

export default {
    name: 'ImageMain',
    props: {
      title: {
        type: String
      },
      description: {
        type: String
      },
      dataKey: {
          type: Number
      }
    },
}
</script>

<style>

</style>