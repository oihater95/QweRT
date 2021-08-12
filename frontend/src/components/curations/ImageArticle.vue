<template>
    <article class="curate-article" :data-key="dataKey">
        <h2 class="curate-heading">{{title}}</h2>
        <p class="curate-paragraph">{{description}}</p>
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
.curate-article {
  padding: .8rem;
  width: 100%;
  margin-right: -100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.curate-heading {
  padding-top: 10px; 
}

.curate-paragraph {
  margin: 0;
  font-size: 1rem;
  line-height: 1.7;
}

.curate-article {
  transform: translateX(-100%);
}
.curate-article:before {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: #D4A12D;
  opacity: 1;
  transition-duration: 0.35s;
  z-index: 1;
}

.curate-article[data-active] ~ .curate-article {
  transform: translateX(100%);
}

.curate-article[data-active] {
  transform: translateX(0%);
}
.curate-article[data-active]:before {
  opacity: 0;
}

</style>