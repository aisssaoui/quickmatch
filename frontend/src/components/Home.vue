<template>
    <v-carousel cycle show-arrows hide-delimiter-background show-arrows-on-hover>
        <v-carousel-item v-for="(item,i) in items" :key="i" :src="item.src" reverse-transition="fade-transition" transition="fade-transition">
        </v-carousel-item>
    </v-carousel>
</template>

<script>
export default {
    data () {
        return {
            items: [
                { src: require("../assets/foot.jpg") },
                { src: require('../assets/volley.jpg') },
                { src: require('../assets/basket.jpg') }
            ],
        }
    },
}
</script>