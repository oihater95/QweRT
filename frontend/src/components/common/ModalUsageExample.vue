<template>
  <div>
    <h1>Test</h1>
    <Modal
      :msg="msg"
      @ok-sign="myFunction"
    />
  </div>
</template>

<script>
import Modal from '@/components/common/Modal'

export default {
  name: 'ModalUsageExample',
  components: {
    Modal,
  },
  data: function () {
    return {
      // positiveBtn과 negativeBtn은 선택사항
      msg: {
        triggerBtn: '버튼',
        title: '타이틀',
        text: '내용<br/>줄바꿈 가능',
        positiveBtn: '확인',
        negativeBtn: '취소',
      },
    }
  },
  methods: {
    // modal 창에서 positiveBtn을 클릭했을 때 실행할 함수
    myFunction: function () {
      console.log('이후 진행할 함수 실행')
    },
  }
}
</script>

<style>

</style>
