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
.ui-article {
  padding: .8rem;
  width: 100%;
  margin-right: -100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.ui-paragraph {
  margin: 0;
  font-size: 1rem;
  line-height: 1.7;
}


</style>