<template>
    <div class="curate-big-image" :data-key="dataKey">
        <img :src="imageUrl" alt="picture">
    </div>
</template>

<script>

export default {
    name: 'ImageMain',
    props: {
      imageUrl: {
        type: String
      },
      dataKey: {
          type: Number
      }
    },
}
</script>

<style scoped>
.curate-big-image {
  width: 100%;
  margin-right: -100%;
}

.curate-big-image img {
  -o-object-fit: scale-down;
  object-fit:scale-down;
  -o-object-position: center 20%;
  object-position: center 20%;
  width: 100%;
  height: 100%;
  display: block;
}

.curate-big-image {
  opacity: 0;
  transform: translateX(-100%);
}
.curate-big-image img {
  transform: scale(0.85);
}

.curate-big-image[data-active] ~ .curate-big-image {
  transform: translateX(100%);
}

.curate-big-image[data-active] {
  opacity: 1;
  transform: translateX(0%);
}
.curate-big-image[data-active] img {
  transform: scale(1);
}

</style>