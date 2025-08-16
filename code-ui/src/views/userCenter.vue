<template>
    <headerVue></headerVue>

    <div class="member-container">
        <div class="member-sidebar">
            <h2>全部菜单</h2>
            <ul>
                <li
                        v-for="(item, index) in menuItems"
                        :key="index"
                        @click="switchTab(item.component)"
                        :class="{ 'active': activeTab === item.component }"
                >
                    <img :src="item.icon"> {{ item.name }}
                </li>
            </ul>
        </div>
        <div class="member-content">
            <h2>{{ getActiveMenuName() }}</h2>
            <component :is="components[activeTab]"></component>
        </div>
    </div>

    <footerVue></footerVue>
</template>

<script setup>
    import { ref } from 'vue'
    import footerVue from "./footer.vue"
    import headerVue from "./header.vue"
    import userStore from "@/store/modules/user"
    import profileVue from "./personalCenter/profile.vue"
    import questionRecodeVue from "./personalCenter/questionRecode.vue"
    import guidanceVue from "./personalCenter/guidance.vue"
    import simulationVue from "./personalCenter/simulation.vue"
    import certificateVue from "./personalCenter/certificate.vue"
    import orderVue from "./personalCenter/order.vue"
    import friendVue from "./personalCenter/friend.vue"
    import profileIcon from '@/assets/icons/个人资料.png'
    import questionRecodeIcon from '@/assets/icons/答题记录.png'
    import payRecodeIcon from '@/assets/icons/个人资料.png'
    import certificateIcon from '@/assets/icons/证书.png'
    import orderIcon from '@/assets/icons/订单.png'
    import friendIcon from '@/assets/icons/好友.png'


    const user = userStore();

    // 定义菜单项
    const menuItems = [
        { name: '个人资料', icon: profileIcon, component: 'profileVue' },
        { name: '我的答题记录', icon: questionRecodeIcon, component: 'questionRecodeVue' },
        { name: '简历指导', icon: questionRecodeIcon, component: 'guidanceVue' },
        { name: '面试模拟', icon: payRecodeIcon, component: 'simulationVue' },
        { name: '我的证书', icon: certificateIcon, component: 'certificateVue' },
        { name: '我的订单', icon: orderIcon, component: 'orderVue' },
        { name: '我的好友', icon: friendIcon, component: 'friendVue' },
    ]

    const components = {
        profileVue,
        questionRecodeVue,
        guidanceVue,
        simulationVue,
        certificateVue,
        orderVue,
        friendVue
    }

    const activeTab = ref('profileVue')

    const switchTab = (componentName) => {
        activeTab.value = componentName
    }

    const getActiveMenuName = () => {
        const activeItem = menuItems.find(item => item.component === activeTab.value)
        return activeItem ? activeItem.name : ''
    }

</script>

<style>
    @import "@/assets/homePage/css/member.css";
    @import "@/assets/homePage/css/index.css";

    /* 会员中心样式 */
    .member-container {
        display: flex;
        width: 90%;
        margin: 20px auto;
    }

    .member-sidebar {
        width: 250px;
        padding: 25px;
        background: #fff;
        border-radius: 8px 0 0 8px;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
        margin-left: 20px;
        background: rgba(255, 255, 245, 0.5);
    }

    .member-sidebar h2 {
        font-size: 18px;
        margin-bottom: 15px;
        color: #333;
        font-weight: normal;
        border-bottom: 1px solid #575757;
        padding: 0 0 10px 0;
    }

    .member-sidebar ul {
        list-style: none;
        padding: 0;
    }

    .member-sidebar li {
        padding: 12px 0;
        cursor: pointer;
        border-bottom: 1px dashed #eee;
        color: #333;
        display: flex;
        align-items: center;
        transition: all 0.3s ease;
    }

    .member-sidebar li.active {
        color: #1890ff;
        background: rgba(255, 255, 255, 0);
    }

    .member-sidebar li.active img {
        filter: invert(39%) sepia(92%) saturate(1745%) hue-rotate(194deg) brightness(97%) contrast(101%);
    }

    .member-sidebar li img {
        width: 20px;
        height: 18px;
        margin-right: 12px;
        opacity: 0.8;
        transition: all 0.3s ease;
    }

    .member-sidebar li:hover {
        color: #1890ff;
    }

    .member-sidebar li:hover img {
        filter: invert(39%) sepia(92%) saturate(1745%) hue-rotate(194deg) brightness(97%) contrast(101%);
    }

    .member-content {
        flex: 1;
        padding: 25px;
        background: #fff;
        border-radius: 0 8px 8px 0;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
        margin-right: 20px;
        background: rgba(255, 255, 245, 0.5);
    }
    .member-content h2 {
        font-size: 18px;
        margin-bottom: 15px;
        color: #333;
        font-weight: normal;
        border-bottom: 1px solid #575757;
        padding: 0 0 10px 0;
    }
</style>