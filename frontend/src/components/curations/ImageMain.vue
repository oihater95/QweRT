<template>
    <div class="ui-big-image" :data-key="dataKey">
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
.ui-big-image {
  width: 100%;
  margin-right: -100%;
}

.ui-big-image img {
  -o-object-fit: cover;
     object-fit: cover;
  -o-object-position: center 20%;
     object-position: center 20%;
  width: 100%;
  height: 100%;
  display: block;
}

</style>