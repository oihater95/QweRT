<template>
    <div class="ui-thumbnail" tabindex="-1" :data-key="dataKey">
        <img class="curation-thumbnail__image" :src="imageUrl" alt="picture" />
        <div class="ui-cuticle" data-flip-key="cuticle"></div>
    </div>
</template>

<script>

export default {
    name: 'ImageThumbnail',
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

</style>