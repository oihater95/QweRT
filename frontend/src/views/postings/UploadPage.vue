<template>
  <v-container>
    <v-row>
      <v-col><DragDrop imgSrc="imgSrc"/></v-col>
      <v-col>
        <div class="container">
          <v-card class="my-5 posting-title">
            <v-text-field
              label="제목"
              :rules="[rules.required]"
              hide-details="auto"
              counter="30"
              maxlength="30"
            ></v-text-field>
          </v-card>
          
          <v-textarea
          class="my-5 pt-5 posting-content"
          solo
          height="180px"
          maxlength="200"
          counter="200"
          :rules="[rules.required]"
          :placeholder="'설명\n\n도용 및 저작권 침해에 주의해주세요'"
          >
          </v-textarea>
          
        </div>
      </v-col>
    </v-row>
    <v-row>
      <RoundedBtn
          v-for="(item, idx) in category" 
          :key="idx"
          :item="item"
          class="category-btns"
          :id="`category-id__${idx}`"
        />
    </v-row>
  </v-container>
</template>

<script>
import DragDrop from "@/components/common/DragDrop"
import RoundedBtn from "@/components/common/RoundedBtn"
import "@/css/postings/UploadPage.scss"

export default {
  name: 'UploadPage',
  components: {
    DragDrop,
    RoundedBtn,
  },
  data() {
    return {
      rules: {
        required: value => !!value || 'Required.',
      },
      category: ["인물", "동물", "식물", "풍경", "음식","건물", "패션", "캐릭터","낙서", "3D", "흑백","기타"],
      toggleCategoryId: [],
    }
  },
  props: {
    imgSrc: {
      type: String,
    }
  },
  methods: {
    clickCategory: function(e) {
      for (var i=0; i < this.category.length; i++) {
        if (e.target.innerText === this.category[i]) {
          if (this.toggleCategoryId.length === 0) {
            this.toggleCategoryId.push(`${i}`)
          } else {
            if (i == this.toggleCategoryId[0]) {
              while (this.toggleCategoryId.length > 0) {
                this.toggleCategoryId.pop()
              }
            } else {
              while (this.toggleCategoryId.length > 0) {
                this.toggleCategoryId.pop()
              }
              this.toggleCategoryId.push(`${i}`)
            }
          }
        }
      }
      // 토글 ON/OFF
      for (var j=0; j < this.category.length; j++) {
        if (j == this.toggleCategoryId[0]) {
          let toggleOn = document.querySelector(`.category-btn__${j}`)
          toggleOn.style = "color: skyblue; border-color: skyblue"
        } else {
          let toggleOff = document.querySelector(`.category-btn__${j}`)
          toggleOff.style = "color: black; border-color: black"
        }
      } 

    }
  },
  mounted() {
    let categories = document.getElementsByClassName('rounded-btn')
    for (var i=0; i < categories.length; i++) {
      categories[i].classList.add(`category-btn__${i}`)
      categories[i].addEventListener('click', this.clickCategory)
    }
  },
}
</script>

<style>

</style>