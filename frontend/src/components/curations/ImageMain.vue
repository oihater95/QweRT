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

<style>
.ui-big-images {
  position: absolute;
  top: -4%;
  left: 10%;
  height: 100%;
  width: 100%;
  overflow:hidden;
  display: flex;
}

</style>