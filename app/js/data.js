/**
 * 芒果TV H5 视频网站 Mock 数据
 * @author Wujun
 */
window.MockData = {
  // ==================== 1. 轮播横幅 ====================
  banners: [
    { id: 1, title: '《狂飙》全网首播 张译张颂文飙戏', imageUrl: 'https://picsum.photos/750/350?random=1', linkId: 'video_1001' },
    { id: 2, title: '乘风破浪的姐姐 第五季 热播中', imageUrl: 'https://picsum.photos/750/350?random=2', linkId: 'video_1002' },
    { id: 3, title: '《狐妖小红娘》竹业篇 定档来袭', imageUrl: 'https://picsum.photos/750/350?random=3', linkId: 'video_1003' },
    { id: 4, title: 'NBA季后赛 湖人VS勇士 精彩回放', imageUrl: 'https://picsum.photos/750/350?random=4', linkId: 'video_1004' },
    { id: 5, title: '《庆余年2》原班人马 年度期待大作', imageUrl: 'https://picsum.photos/750/350?random=5', linkId: 'video_1005' }
  ],

  // ==================== 2. 分类频道 ====================
  categories: [
    { id: 1, name: '推荐', icon: '🏠', link: '/recommend' },
    { id: 2, name: '短剧', icon: '🎭', link: '/short-drama' },
    { id: 3, name: '动漫', icon: '🐉', link: '/anime' },
    { id: 4, name: '电影', icon: '🎬', link: '/movie' },
    { id: 5, name: '电视剧', icon: '📺', link: '/tv' },
    { id: 6, name: '综艺', icon: '🎪', link: '/variety' },
    { id: 7, name: '体育', icon: '⚽', link: '/sports' },
    { id: 8, name: '小说', icon: '📖', link: '/novel' }
  ],

  // ==================== 3. 热播排行 ====================
  hotVideos: [
    { id: 1, rank: 1, title: '狂飙', thumbnail: 'https://picsum.photos/400/225?random=6', tags: ['犯罪', '剧情', '悬疑'], score: 9.5, episodeCount: 39, views: '128.6亿' },
    { id: 2, rank: 2, title: '庆余年第二季', thumbnail: 'https://picsum.photos/400/225?random=7', tags: ['古装', '权谋', '喜剧'], score: 9.3, episodeCount: 36, views: '115.2亿' },
    { id: 3, rank: 3, title: '狐妖小红娘', thumbnail: 'https://picsum.photos/400/225?random=8', tags: ['玄幻', '爱情', '仙侠'], score: 9.1, episodeCount: 40, views: '98.7亿' },
    { id: 4, rank: 4, title: '乘风破浪的姐姐', thumbnail: 'https://picsum.photos/400/225?random=9', tags: ['综艺', '竞演', '女性'], score: 8.9, episodeCount: 12, views: '87.3亿' },
    { id: 5, rank: 5, title: '斗破苍穹年番', thumbnail: 'https://picsum.photos/400/225?random=10', tags: ['动漫', '玄幻', '热血'], score: 9.0, episodeCount: 52, views: '76.5亿' },
    { id: 6, rank: 6, title: '长相思', thumbnail: 'https://picsum.photos/400/225?random=11', tags: ['古装', '爱情', '神话'], score: 9.2, episodeCount: 39, views: '68.9亿' },
    { id: 7, rank: 7, title: '花儿与少年', thumbnail: 'https://picsum.photos/400/225?random=12', tags: ['综艺', '旅行', '真人秀'], score: 8.7, episodeCount: 10, views: '62.1亿' },
    { id: 8, rank: 8, title: '繁花', thumbnail: 'https://picsum.photos/400/225?random=13', tags: ['年代', '都市', '商战'], score: 9.4, episodeCount: 30, views: '55.8亿' },
    { id: 9, rank: 9, title: '完美世界', thumbnail: 'https://picsum.photos/400/225?random=14', tags: ['动漫', '玄幻', '冒险'], score: 8.8, episodeCount: 78, views: '48.3亿' },
    { id: 10, rank: 10, title: '中餐厅第八季', thumbnail: 'https://picsum.photos/400/225?random=15', tags: ['综艺', '美食', '经营'], score: 8.6, episodeCount: 12, views: '42.7亿' }
  ],

  // ==================== 4. 推荐视频 ====================
  recommendedVideos: [
    { id: 1, title: '繁花', cover: 'https://picsum.photos/400/225?random=16', description: '九十年代的上海，人人争上游，阿宝也变成了宝总', rating: 9.4, category: '电视剧' },
    { id: 2, title: '热辣滚烫', cover: 'https://picsum.photos/400/225?random=17', description: '贾玲导演并主演的励志喜剧电影', rating: 9.0, category: '电影' },
    { id: 3, title: '大侦探第九季', cover: 'https://picsum.photos/400/225?random=18', description: '明星大侦探推理综艺全新升级', rating: 8.9, category: '综艺' },
    { id: 4, title: '第二十条', cover: 'https://picsum.photos/400/225?random=19', description: '张艺谋导演现实主义题材力作', rating: 9.2, category: '电影' },
    { id: 5, title: '与凤行', cover: 'https://picsum.photos/400/225?random=20', description: '赵丽颖林更新再度携手古装仙侠', rating: 8.8, category: '电视剧' },
    { id: 6, title: '唐人街探案3', cover: 'https://picsum.photos/400/225?random=21', description: '王宝强刘昊然东京探案之旅', rating: 8.5, category: '电影' },
    { id: 7, title: '乘风2024', cover: 'https://picsum.photos/400/225?random=22', description: '全女性音乐竞演真人秀', rating: 8.7, category: '综艺' },
    { id: 8, title: '凡人修仙传', cover: 'https://picsum.photos/400/225?random=23', description: '韩立修仙之路，动漫巨制', rating: 9.3, category: '动漫' },
    { id: 9, title: '猎冰', cover: 'https://picsum.photos/400/225?random=24', description: '张颂文主演缉毒题材硬核刑侦剧', rating: 8.4, category: '电视剧' },
    { id: 10, title: '飞驰人生2', cover: 'https://picsum.photos/400/225?random=25', description: '沈腾主演赛车题材续作', rating: 9.1, category: '电影' },
    { id: 11, title: '歌手2024', cover: 'https://picsum.photos/400/225?random=26', description: '华语乐坛顶级歌手巅峰对决', rating: 8.6, category: '综艺' },
    { id: 12, title: '一念永恒', cover: 'https://picsum.photos/400/225?random=27', description: '白小纯修仙搞笑日常番', rating: 8.9, category: '动漫' },
    { id: 13, title: '你好星期六', cover: 'https://picsum.photos/400/225?random=28', description: '何炅主持的欢乐综艺', rating: 8.3, category: '综艺' },
    { id: 14, title: '追风者', cover: 'https://picsum.photos/400/225?random=29', description: '王一博李沁主演民国谍战剧', rating: 8.7, category: '电视剧' },
    { id: 15, title: '熊出没·逆转时空', cover: 'https://picsum.photos/400/225?random=30', description: '熊大熊二光头强穿越时空冒险', rating: 8.8, category: '电影' },
    { id: 16, title: '斗罗大陆Ⅱ', cover: 'https://picsum.photos/400/225?random=31', description: '唐三重生再创辉煌', rating: 9.0, category: '动漫' },
    { id: 17, title: '种地吧第二季', cover: 'https://picsum.photos/400/225?random=32', description: '十个少年农耕劳作真人秀', rating: 8.5, category: '综艺' },
    { id: 18, title: '不虚此行', cover: 'https://picsum.photos/400/225?random=33', description: '胡歌主演写悼词的温暖故事', rating: 8.9, category: '电影' },
    { id: 19, title: '仙逆', cover: 'https://picsum.photos/400/225?random=34', description: '王林逆天改命修真动漫', rating: 9.1, category: '动漫' },
    { id: 20, title: '新生', cover: 'https://picsum.photos/400/225?random=35', description: '井柏然周依然主演悬疑短剧', rating: 8.6, category: '电视剧' }
  ],

  // ==================== 5. 短剧 ====================
  shortDramas: [
    { id: 1, title: '霸道总裁的天价前妻', cover: 'https://picsum.photos/400/225?random=36', episodeCount: 80, status: '连载', hot: true },
    { id: 2, title: '重生之都市修仙', cover: 'https://picsum.photos/400/225?random=37', episodeCount: 100, status: '完结', hot: true },
    { id: 3, title: '夫人在上总裁在下', cover: 'https://picsum.photos/400/225?random=38', episodeCount: 60, status: '连载', hot: true },
    { id: 4, title: '闪婚后被大佬宠上天', cover: 'https://picsum.photos/400/225?random=39', episodeCount: 75, status: '连载', hot: false },
    { id: 5, title: '战神归来', cover: 'https://picsum.photos/400/225?random=40', episodeCount: 90, status: '完结', hot: true },
    { id: 6, title: '替嫁新娘的腹黑老公', cover: 'https://picsum.photos/400/225?random=41', episodeCount: 55, status: '连载', hot: false },
    { id: 7, title: '穿越古代做王妃', cover: 'https://picsum.photos/400/225?random=42', episodeCount: 70, status: '完结', hot: false },
    { id: 8, title: '权宠天下', cover: 'https://picsum.photos/400/225?random=43', episodeCount: 85, status: '连载', hot: true },
    { id: 9, title: '总裁的替身新娘', cover: 'https://picsum.photos/400/225?random=44', episodeCount: 50, status: '完结', hot: false },
    { id: 10, title: '神医下山', cover: 'https://picsum.photos/400/225?random=45', episodeCount: 95, status: '连载', hot: true },
    { id: 11, title: '冷面王爷的独宠', cover: 'https://picsum.photos/400/225?random=46', episodeCount: 65, status: '连载', hot: false },
    { id: 12, title: '逆袭人生从破产开始', cover: 'https://picsum.photos/400/225?random=47', episodeCount: 40, status: '完结', hot: false },
    { id: 13, title: '豪门千金归来', cover: 'https://picsum.photos/400/225?random=48', episodeCount: 78, status: '连载', hot: true },
    { id: 14, title: '至尊神皇在都市', cover: 'https://picsum.photos/400/225?random=49', episodeCount: 88, status: '连载', hot: false },
    { id: 15, title: '女王驾到', cover: 'https://picsum.photos/400/225?random=50', episodeCount: 45, status: '完结', hot: false }
  ],

  // ==================== 6. 短视频 ====================
  shortVideos: [
    { id: 1, title: '猫咪第一次看到雪的反应，太可爱了', cover: 'https://picsum.photos/400/225?random=51', duration: '00:45', author: '萌宠日记', likes: '23.5万', views: '156.8万' },
    { id: 2, title: '一分钟学会做红烧肉', cover: 'https://picsum.photos/400/225?random=52', duration: '01:02', author: '厨房小能手', likes: '18.2万', views: '89.3万' },
    { id: 3, title: '街头艺人惊艳全场的表演', cover: 'https://picsum.photos/400/225?random=53', duration: '00:58', author: '城市发现', likes: '35.7万', views: '210.5万' },
    { id: 4, title: '女生必学的三个穿搭技巧', cover: 'https://picsum.photos/400/225?random=54', duration: '00:52', author: '时尚达人Lin', likes: '12.8万', views: '67.4万' },
    { id: 5, title: '搞笑笑到肚子疼的合集', cover: 'https://picsum.photos/400/225?random=55', duration: '01:15', author: '快乐源泉', likes: '42.1万', views: '320.7万' },
    { id: 6, title: '大学生宿舍神操作', cover: 'https://picsum.photos/400/225?random=56', duration: '00:38', author: '校园趣事', likes: '9.5万', views: '45.2万' },
    { id: 7, title: '十分钟搞定上班族便当', cover: 'https://picsum.photos/400/225?random=57', duration: '01:20', author: '便当女王', likes: '15.3万', views: '78.9万' },
    { id: 8, title: '极限运动第一视角震撼体验', cover: 'https://picsum.photos/400/225?random=58', duration: '00:55', author: '冒险家小飞', likes: '28.6万', views: '198.3万' },
    { id: 9, title: '零基础学会弹吉他', cover: 'https://picsum.photos/400/225?random=59', duration: '01:08', author: '音乐教室', likes: '11.2万', views: '56.7万' },
    { id: 10, title: '这个小魔术让朋友惊呆了', cover: 'https://picsum.photos/400/225?random=60', duration: '00:42', author: '魔术师阿杰', likes: '20.4万', views: '134.2万' },
    { id: 11, title: '旅行最美的十个瞬间', cover: 'https://picsum.photos/400/225?random=61', duration: '01:30', author: '旅行者', likes: '31.7万', views: '256.8万' },
    { id: 12, title: '如何用手机拍出大片', cover: 'https://picsum.photos/400/225?random=62', duration: '00:50', author: '摄影老王', likes: '16.9万', views: '88.1万' },
    { id: 13, title: '小朋友的逻辑你永远猜不到', cover: 'https://picsum.photos/400/225?random=63', duration: '00:36', author: '萌娃日常', likes: '26.3万', views: '178.6万' },
    { id: 14, title: '三分钟瑜伽拉伸跟练', cover: 'https://picsum.photos/400/225?random=64', duration: '01:25', author: '瑜伽教练Amy', likes: '14.7万', views: '72.3万' },
    { id: 15, title: '这舞蹈也太帅了吧', cover: 'https://picsum.photos/400/225?random=65', duration: '00:48', author: '舞动青春', likes: '38.9万', views: '289.4万' }
  ],

  // ==================== 7. 长视频/电影 ====================
  longVideos: [
    { id: 1, title: '狂飙', cover: 'https://picsum.photos/400/225?random=66', year: 2023, rating: 9.5, director: '徐纪周', actors: ['张译', '张颂文', '李一桐', '吴刚'], genreTags: ['犯罪', '剧情', '反黑'], description: '京海市一线刑警安欣与黑恶势力展开长达二十年正邪较量' },
    { id: 2, title: '流浪地球2', cover: 'https://picsum.photos/400/225?random=67', year: 2023, rating: 9.2, director: '郭帆', actors: ['吴京', '刘德华', '李雪健', '沙溢'], genreTags: ['科幻', '灾难', '冒险'], description: '太阳即将毁灭，人类在地球表面建造巨型推进器寻找新家园' },
    { id: 3, title: '繁花', cover: 'https://picsum.photos/400/225?random=68', year: 2024, rating: 9.4, director: '王家卫', actors: ['胡歌', '马伊琍', '唐嫣', '辛芷蕾'], genreTags: ['年代', '都市', '商战'], description: '九十年代上海滩，阿宝从市井小人物逆袭为商界传奇' },
    { id: 4, title: '封神第一部', cover: 'https://picsum.photos/400/225?random=69', year: 2023, rating: 9.0, director: '乌尔善', actors: ['费翔', '李雪健', '黄渤', '于适'], genreTags: ['奇幻', '史诗', '动作'], description: '商王殷寿暴虐无道，昆仑仙人姜子牙携封神榜下山' },
    { id: 5, title: '长津湖', cover: 'https://picsum.photos/400/225?random=70', year: 2021, rating: 9.3, director: '陈凯歌', actors: ['吴京', '易烊千玺', '段奕宏', '朱亚文'], genreTags: ['战争', '历史', '动作'], description: '1950年中国人民志愿军赴朝作战，在长津湖展开殊死搏斗' },
    { id: 6, title: '长相思', cover: 'https://picsum.photos/400/225?random=71', year: 2023, rating: 9.2, director: '秦榛', actors: ['杨紫', '张晚意', '邓为', '檀健次'], genreTags: ['古装', '爱情', '神话'], description: '上古时期人神妖混居，小夭流落大荒历经百般磨难' },
    { id: 7, title: '满江红', cover: 'https://picsum.photos/400/225?random=72', year: 2023, rating: 8.8, director: '张艺谋', actors: ['沈腾', '易烊千玺', '张译', '雷佳音'], genreTags: ['悬疑', '喜剧', '历史'], description: '南宋绍兴年间，岳飞死后四年，秦桧率兵与金国会谈' },
    { id: 8, title: '漫长的季节', cover: 'https://picsum.photos/400/225?random=73', year: 2023, rating: 9.4, director: '辛爽', actors: ['范伟', '秦昊', '陈明昊', '李庚希'], genreTags: ['悬疑', '家庭', '犯罪'], description: '出租车司机王响和妹夫龚彪联手调查套牌车案' },
    { id: 9, title: '三体', cover: 'https://picsum.photos/400/225?random=74', year: 2023, rating: 8.7, director: '杨磊', actors: ['张鲁一', '于和伟', '陈瑾', '王子文'], genreTags: ['科幻', '悬疑', '剧情'], description: '纳米科学家汪淼被卷入神秘组织，人类文明面临外星威胁' },
    { id: 10, title: '山海情', cover: 'https://picsum.photos/400/225?random=75', year: 2021, rating: 9.3, director: '孔笙', actors: ['黄轩', '张嘉益', '闫妮', '热依扎'], genreTags: ['扶贫', '剧情', '励志'], description: '二十世纪九十年代宁夏西海固人民异地搬迁脱贫的故事' },
    { id: 11, title: '八角笼中', cover: 'https://picsum.photos/400/225?random=76', year: 2023, rating: 8.6, director: '王宝强', actors: ['王宝强', '陈永胜', '史彭元', '王迅'], genreTags: ['剧情', '动作', '励志'], description: '向腾辉倾注心血把一群孤儿培养成格斗冠军的故事' },
    { id: 12, title: '隐秘的角落', cover: 'https://picsum.photos/400/225?random=77', year: 2020, rating: 9.2, director: '辛爽', actors: ['秦昊', '王景春', '荣梓杉', '史彭元'], genreTags: ['悬疑', '犯罪', '家庭'], description: '沿海小城三个孩子在景区游玩时无意拍摄记录了一场谋杀' },
    { id: 13, title: '消失的她', cover: 'https://picsum.photos/400/225?random=78', year: 2023, rating: 8.5, director: '崔睿', actors: ['朱一龙', '倪妮', '文咏珊', '杜江'], genreTags: ['悬疑', '犯罪', '剧情'], description: '何非的妻子在海外度假时神秘失踪，一场精心策划的阴谋浮出水面' },
    { id: 14, title: '人世间', cover: 'https://picsum.photos/400/225?random=79', year: 2022, rating: 9.3, director: '李路', actors: ['雷佳音', '辛柏青', '宋佳', '殷桃'], genreTags: ['年代', '剧情', '家庭'], description: '周家三代人在五十年岁月中经历的跌宕起伏人生故事' },
    { id: 15, title: '独行月球', cover: 'https://picsum.photos/400/225?random=80', year: 2022, rating: 8.4, director: '张吃鱼', actors: ['沈腾', '马丽', '常远', '黄才伦'], genreTags: ['喜剧', '科幻', '剧情'], description: '人类为抵御小行星撞击部署月盾计划，独孤月被意外遗落月球' }
  ],

  // ==================== 8. 动漫 ====================
  comics: [
    { id: 1, title: '斗破苍穹', cover: 'https://picsum.photos/400/225?random=81', author: '天蚕土豆', status: '连载', latestChapter: '第356话 最终决战', category: '玄幻' },
    { id: 2, title: '一人之下', cover: 'https://picsum.photos/400/225?random=82', author: '米二', status: '连载', latestChapter: '第628话 天师下山', category: '奇幻' },
    { id: 3, title: '全职高手', cover: 'https://picsum.photos/400/225?random=83', author: '蝴蝶蓝', status: '完结', latestChapter: '第1728话 荣耀巅峰', category: '电竞' },
    { id: 4, title: '少年歌行', cover: 'https://picsum.photos/400/225?random=84', author: '周木楠', status: '连载', latestChapter: '第192话 雪落山庄', category: '武侠' },
    { id: 5, title: '星辰变', cover: 'https://picsum.photos/400/225?random=85', author: '我吃西红柿', status: '连载', latestChapter: '第215话 鸿蒙空间', category: '修真' },
    { id: 6, title: '镇魂街', cover: 'https://picsum.photos/400/225?random=86', author: '许辰', status: '连载', latestChapter: '第387话 风云再起', category: '玄幻' },
    { id: 7, title: '武动乾坤', cover: 'https://picsum.photos/400/225?random=87', author: '天蚕土豆', status: '完结', latestChapter: '第420话 乾坤之主', category: '玄幻' },
    { id: 8, title: '凡人修仙传', cover: 'https://picsum.photos/400/225?random=88', author: '忘语', status: '连载', latestChapter: '第298话 灵界篇', category: '修真' },
    { id: 9, title: '妖神记', cover: 'https://picsum.photos/400/225?random=89', author: '发飙的蜗牛', status: '连载', latestChapter: '第365话 转世重生', category: '奇幻' },
    { id: 10, title: '斗罗大陆', cover: 'https://picsum.photos/400/225?random=90', author: '唐家三少', status: '完结', latestChapter: '第648话 双神之战', category: '玄幻' },
    { id: 11, title: '完美世界', cover: 'https://picsum.photos/400/225?random=91', author: '辰东', status: '连载', latestChapter: '第186话 虚神界', category: '玄幻' },
    { id: 12, title: '元龙', cover: 'https://picsum.photos/400/225?random=92', author: '任怨', status: '连载', latestChapter: '第154话 无字天书', category: '穿越' }
  ],

  // ==================== 9. 小说 ====================
  novels: [
    { id: 1, title: '斗破苍穹', cover: 'https://picsum.photos/400/225?random=93', author: '天蚕土豆', status: '完结', wordCount: '532.6万', category: '玄幻', description: '天才少年萧炎在创造了家族纪录后突然沦为废人，种种打击接踵而至。就在他即将绝望的时候，一缕灵魂从他手上的戒指里浮现...', chapters: [
      { id: 101, title: '第一章 陨落的天才', free: true },
      { id: 102, title: '第二章 斗气大陆', free: true },
      { id: 103, title: '第三章 客人', free: false }
    ] },
    { id: 2, title: '诛仙', cover: 'https://picsum.photos/400/225?random=94', author: '萧鼎', status: '完结', wordCount: '268.3万', category: '仙侠', description: '天地不仁，以万物为刍狗。少年张小凡因缘际会进入青云门修行...', chapters: [
      { id: 201, title: '第一章 青云', free: true },
      { id: 202, title: '第二章 迷局', free: true },
      { id: 203, title: '第三章 宏愿', free: false }
    ] },
    { id: 3, title: '全职高手', cover: 'https://picsum.photos/400/225?random=95', author: '蝴蝶蓝', status: '完结', wordCount: '534.8万', category: '游戏', description: '网游荣耀中被誉为教科书级别的顶尖高手叶修，因为种种原因遭到俱乐部驱逐...', chapters: [
      { id: 301, title: '第一章 被驱逐的高手', free: true },
      { id: 302, title: '第二章 新区开荒', free: true },
      { id: 303, title: '第三章 君莫笑', free: false }
    ] },
    { id: 4, title: '龙王殿', cover: 'https://picsum.photos/400/225?random=96', author: '青衫仗剑', status: '连载', wordCount: '186.5万', category: '都市', description: '奉命下山成婚的龙王殿少主，却成了一个受尽白眼的上门女婿...', chapters: [
      { id: 401, title: '第一章 奉命成婚', free: true },
      { id: 402, title: '第二章 忍辱负重', free: true },
      { id: 403, title: '第三章 龙之逆鳞', free: false }
    ] },
    { id: 5, title: '凡人修仙传', cover: 'https://picsum.photos/400/225?random=97', author: '忘语', status: '完结', wordCount: '746.2万', category: '修真', description: '一个普通的山村穷小子，偶然之下进入到当地江湖小门派，成了一名记名弟子...', chapters: [
      { id: 501, title: '第一章 山边小村', free: true },
      { id: 502, title: '第二章 七玄门', free: true },
      { id: 503, title: '第三章 神秘小瓶', free: false }
    ] },
    { id: 6, title: '星门', cover: 'https://picsum.photos/400/225?random=98', author: '老鹰吃小鸡', status: '连载', wordCount: '312.7万', category: '玄幻', description: '传说在古老的星空深处有一道门，推开它就能获得无尽的力量...', chapters: [
      { id: 601, title: '第一章 银月城', free: true },
      { id: 602, title: '第二章 星光淬体', free: true },
      { id: 603, title: '第三章 遗迹探险', free: false }
    ] },
    { id: 7, title: '大奉打更人', cover: 'https://picsum.photos/400/225?random=99', author: '卖报小郎君', status: '连载', wordCount: '258.4万', category: '仙侠', description: '现代警察许七安穿越到大奉王朝，成为一名负责夜间治安的打更人...', chapters: [
      { id: 701, title: '第一章 穿越打更人', free: true },
      { id: 702, title: '第二章 深夜命案', free: true },
      { id: 703, title: '第三章 超凡觉醒', free: false }
    ] },
    { id: 8, title: '剑来', cover: 'https://picsum.photos/400/225?random=100', author: '烽火戏诸侯', status: '连载', wordCount: '428.6万', category: '仙侠', description: '大千世界无奇不有。我叫陈平安，我唯一的本事就是打不过就跑...', chapters: [
      { id: 801, title: '第一章 泥瓶巷', free: true },
      { id: 802, title: '第二章 搬山猿', free: true },
      { id: 803, title: '第三章 少年行', free: false }
    ] },
    { id: 9, title: '万古神帝', cover: 'https://picsum.photos/400/225?random=93', author: '飞天鱼', status: '连载', wordCount: '521.3万', category: '玄幻', description: '八百年前天地间第一强者张若尘，在万界强者的围攻下身死道消...', chapters: [
      { id: 901, title: '第一章 重生归来', free: true },
      { id: 902, title: '第二章 时空秘典', free: true },
      { id: 903, title: '第三章 未婚妻', free: false }
    ] },
    { id: 10, title: '神医傻妃', cover: 'https://picsum.photos/400/225?random=94', author: '顾西音', status: '完结', wordCount: '198.2万', category: '言情', description: '现代神医一朝穿越成为王府傻妃，且看她如何翻手为云覆手为雨...', chapters: [
      { id: 1001, title: '第一章 穿越傻妃', free: true },
      { id: 1002, title: '第二章 冷面王爷', free: true },
      { id: 1003, title: '第三章 深宫斗法', free: false }
    ] },
    { id: 11, title: '逆天邪神', cover: 'https://picsum.photos/400/225?random=95', author: '火星引力', status: '连载', wordCount: '356.8万', category: '玄幻', description: '掌天毒之珠，承邪神之血，修逆转乾坤之力，一代邪神，君临天下...', chapters: [
      { id: 1101, title: '第一章 流云城', free: true },
      { id: 1102, title: '第二章 邪神之种', free: true },
      { id: 1103, title: '第三章 初露锋芒', free: false }
    ] },
    { id: 12, title: '牧神记', cover: 'https://picsum.photos/400/225?random=96', author: '宅猪', status: '完结', wordCount: '482.1万', category: '仙侠', description: '大墟的祖训说，天黑别出门。残老村中，秦牧放牧着一群可怕的神魔...', chapters: [
      { id: 1201, title: '第一章 残老村', free: true },
      { id: 1202, title: '第二章 霸体初成', free: true },
      { id: 1203, title: '第三章 走出大墟', free: false }
    ] }
  ],

  // ==================== 10. 音频/有声书 ====================
  audios: [
    { id: 1, title: '鬼吹灯之精绝古城', cover: 'https://picsum.photos/400/225?random=81', author: '天下霸唱', episodeCount: 120, plays: '5623.4万', category: '悬疑' },
    { id: 2, title: '明朝那些事儿', cover: 'https://picsum.photos/400/225?random=82', author: '当年明月', episodeCount: 268, plays: '1.2亿', category: '历史' },
    { id: 3, title: '三体（广播剧）', cover: 'https://picsum.photos/400/225?random=83', author: '刘慈欣', episodeCount: 80, plays: '3821.6万', category: '科幻' },
    { id: 4, title: '盗墓笔记', cover: 'https://picsum.photos/400/225?random=84', author: '南派三叔', episodeCount: 156, plays: '6890.3万', category: '悬疑' },
    { id: 5, title: '斗破苍穹有声版', cover: 'https://picsum.photos/400/225?random=85', author: '天蚕土豆', episodeCount: 520, plays: '3.5亿', category: '玄幻' },
    { id: 6, title: '郭德纲相声精选', cover: 'https://picsum.photos/400/225?random=86', author: '郭德纲', episodeCount: 200, plays: '2.8亿', category: '相声' },
    { id: 7, title: '活着', cover: 'https://picsum.photos/400/225?random=87', author: '余华', episodeCount: 22, plays: '1985.7万', category: '文学' },
    { id: 8, title: '平凡的世界', cover: 'https://picsum.photos/400/225?random=88', author: '路遥', episodeCount: 152, plays: '2563.9万', category: '文学' },
    { id: 9, title: '雪中悍刀行', cover: 'https://picsum.photos/400/225?random=89', author: '烽火戏诸侯', episodeCount: 380, plays: '4567.2万', category: '武侠' },
    { id: 10, title: '庆余年有声小说', cover: 'https://picsum.photos/400/225?random=90', author: '猫腻', episodeCount: 420, plays: '1.1亿', category: '权谋' }
  ],

  // ==================== 11. 体育 ====================
  sports: [
    { id: 1, title: 'NBA季后赛 勇士VS国王 G7决胜局', cover: 'https://picsum.photos/400/225?random=71', matchInfo: '勇士 120:115 国王', date: '2024-05-18' },
    { id: 2, title: '英超第38轮 曼城VS阿森纳 冠军之战', cover: 'https://picsum.photos/400/225?random=72', matchInfo: '曼城 3:1 阿森纳', date: '2024-05-19' },
    { id: 3, title: '中超联赛 上海海港VS北京国安', cover: 'https://picsum.photos/400/225?random=73', matchInfo: '上海海港 2:0 北京国安', date: '2024-05-15' },
    { id: 4, title: '世界女排联赛 中国VS巴西', cover: 'https://picsum.photos/400/225?random=74', matchInfo: '中国 3:2 巴西', date: '2024-05-16' },
    { id: 5, title: '法网公开赛 男单决赛', cover: 'https://picsum.photos/400/225?random=75', matchInfo: '德约科维奇 VS 阿尔卡拉斯', date: '2024-06-08' },
    { id: 6, title: '德甲第34轮 拜仁慕尼黑VS多特蒙德', cover: 'https://picsum.photos/400/225?random=76', matchInfo: '拜仁 4:2 多特蒙德', date: '2024-05-18' },
    { id: 7, title: 'CBA总决赛 辽宁VS广东 G5', cover: 'https://picsum.photos/400/225?random=77', matchInfo: '辽宁 108:102 广东', date: '2024-05-20' },
    { id: 8, title: '乒乓球世锦赛 男单决赛', cover: 'https://picsum.photos/400/225?random=78', matchInfo: '樊振东 VS 马龙', date: '2024-05-25' },
    { id: 9, title: 'UFC冠军赛 张伟丽卫冕战', cover: 'https://picsum.photos/400/225?random=79', matchInfo: '张伟丽 VS 罗斯', date: '2024-06-15' },
    { id: 10, title: '中超联赛 山东泰山VS上海申花', cover: 'https://picsum.photos/400/225?random=80', matchInfo: '山东泰山 1:1 上海申花', date: '2024-05-22' }
  ],

  // ==================== 12. 视频合集 ====================
  collections: [
    { id: 1, name: '古装仙侠必看TOP10', cover: 'https://picsum.photos/400/225?random=36', count: 10, videos: [
      { title: '长相思', cover: 'https://picsum.photos/400/225?random=11' },
      { title: '与凤行', cover: 'https://picsum.photos/400/225?random=20' },
      { title: '狐妖小红娘', cover: 'https://picsum.photos/400/225?random=8' }
    ] },
    { id: 2, name: '沈腾电影合集 笑到停不下来', cover: 'https://picsum.photos/400/225?random=37', count: 8, videos: [
      { title: '独行月球', cover: 'https://picsum.photos/400/225?random=80' },
      { title: '满江红', cover: 'https://picsum.photos/400/225?random=72' },
      { title: '飞驰人生2', cover: 'https://picsum.photos/400/225?random=25' }
    ] },
    { id: 3, name: '热门国漫大合集', cover: 'https://picsum.photos/400/225?random=38', count: 20, videos: [
      { title: '斗破苍穹', cover: 'https://picsum.photos/400/225?random=10' },
      { title: '完美世界', cover: 'https://picsum.photos/400/225?random=14' },
      { title: '凡人修仙传', cover: 'https://picsum.photos/400/225?random=23' }
    ] },
    { id: 4, name: '悬疑推理剧大赏', cover: 'https://picsum.photos/400/225?random=39', count: 12, videos: [
      { title: '漫长的季节', cover: 'https://picsum.photos/400/225?random=73' },
      { title: '隐秘的角落', cover: 'https://picsum.photos/400/225?random=77' },
      { title: '消失的她', cover: 'https://picsum.photos/400/225?random=78' }
    ] },
    { id: 5, name: '高分华语电影精选', cover: 'https://picsum.photos/400/225?random=40', count: 15, videos: [
      { title: '流浪地球2', cover: 'https://picsum.photos/400/225?random=67' },
      { title: '封神第一部', cover: 'https://picsum.photos/400/225?random=69' },
      { title: '长津湖', cover: 'https://picsum.photos/400/225?random=70' }
    ] },
    { id: 6, name: '新番动漫抢先看', cover: 'https://picsum.photos/400/225?random=41', count: 18, videos: [
      { title: '仙逆', cover: 'https://picsum.photos/400/225?random=34' },
      { title: '斗罗大陆Ⅱ', cover: 'https://picsum.photos/400/225?random=31' },
      { title: '一念永恒', cover: 'https://picsum.photos/400/225?random=27' }
    ] },
    { id: 7, name: '综艺名场面合集', cover: 'https://picsum.photos/400/225?random=42', count: 25, videos: [
      { title: '乘风2024', cover: 'https://picsum.photos/400/225?random=22' },
      { title: '大侦探第九季', cover: 'https://picsum.photos/400/225?random=18' },
      { title: '歌手2024', cover: 'https://picsum.photos/400/225?random=26' }
    ] },
    { id: 8, name: '张译张颂文作品集', cover: 'https://picsum.photos/400/225?random=43', count: 6, videos: [
      { title: '狂飙', cover: 'https://picsum.photos/400/225?random=6' },
      { title: '猎冰', cover: 'https://picsum.photos/400/225?random=24' },
      { title: '满江红', cover: 'https://picsum.photos/400/225?random=72' }
    ] }
  ],

  // ==================== 13. 演员/明星 ====================
  actors: [
    { id: 1, name: '张译', avatar: 'https://picsum.photos/200/200?random=101', description: '中国内地男演员，作品包括《狂飙》《士兵突击》《我的团长我的团》等', relatedVideos: [{ title: '狂飙', cover: 'https://picsum.photos/400/225?random=6' }, { title: '满江红', cover: 'https://picsum.photos/400/225?random=72' }] },
    { id: 2, name: '张颂文', avatar: 'https://picsum.photos/200/200?random=102', description: '中国内地男演员，代表作品《狂飙》《隐秘的角落》《猎冰》', relatedVideos: [{ title: '狂飙', cover: 'https://picsum.photos/400/225?random=6' }, { title: '猎冰', cover: 'https://picsum.photos/400/225?random=24' }] },
    { id: 3, name: '胡歌', avatar: 'https://picsum.photos/200/200?random=103', description: '中国内地男演员，作品《琅琊榜》《繁花》《仙剑奇侠传》', relatedVideos: [{ title: '繁花', cover: 'https://picsum.photos/400/225?random=13' }, { title: '不虚此行', cover: 'https://picsum.photos/400/225?random=33' }] },
    { id: 4, name: '吴京', avatar: 'https://picsum.photos/200/200?random=104', description: '中国内地男演员、导演，代表作《战狼》《流浪地球》《长津湖》', relatedVideos: [{ title: '流浪地球2', cover: 'https://picsum.photos/400/225?random=67' }, { title: '长津湖', cover: 'https://picsum.photos/400/225?random=70' }] },
    { id: 5, name: '杨紫', avatar: 'https://picsum.photos/200/200?random=105', description: '中国内地女演员，代表作品《长相思》《亲爱的热爱的》《香蜜沉沉烬如霜》', relatedVideos: [{ title: '长相思', cover: 'https://picsum.photos/400/225?random=11' }] },
    { id: 6, name: '赵丽颖', avatar: 'https://picsum.photos/200/200?random=106', description: '中国内地女演员，作品《与凤行》《知否知否》《花千骨》', relatedVideos: [{ title: '与凤行', cover: 'https://picsum.photos/400/225?random=20' }] },
    { id: 7, name: '沈腾', avatar: 'https://picsum.photos/200/200?random=107', description: '中国内地男演员、喜剧明星，作品《满江红》《独行月球》《飞驰人生》', relatedVideos: [{ title: '独行月球', cover: 'https://picsum.photos/400/225?random=80' }, { title: '飞驰人生2', cover: 'https://picsum.photos/400/225?random=25' }] },
    { id: 8, name: '易烊千玺', avatar: 'https://picsum.photos/200/200?random=108', description: '中国内地男演员、歌手，作品《少年的你》《长津湖》《满江红》', relatedVideos: [{ title: '长津湖', cover: 'https://picsum.photos/400/225?random=70' }, { title: '满江红', cover: 'https://picsum.photos/400/225?random=72' }] },
    { id: 9, name: '雷佳音', avatar: 'https://picsum.photos/200/200?random=109', description: '中国内地男演员，作品《人世间》《满江红》《我的前半生》', relatedVideos: [{ title: '人世间', cover: 'https://picsum.photos/400/225?random=79' }, { title: '满江红', cover: 'https://picsum.photos/400/225?random=72' }] },
    { id: 10, name: '热依扎', avatar: 'https://picsum.photos/200/200?random=110', description: '中国内地女演员，作品《山海情》《长安十二时辰》', relatedVideos: [{ title: '山海情', cover: 'https://picsum.photos/400/225?random=75' }] }
  ],

  // ==================== 14. 社区/论坛帖子 ====================
  posts: [
    { id: 1, title: '《狂飙》里高启强到底算不算反派？', content: '刚追完狂飙，感觉高启强这个角色塑造得太好了。从卖鱼小贩到黑老大，一路走过来，你说他坏吧，他也有情有义...', author: '追剧达人', avatar: 'https://picsum.photos/100/100?random=201', likes: 2580, comments: 892, time: '2小时前', images: [] },
    { id: 2, title: '推荐几部巨好看的国漫', content: '斗破苍穹、完美世界、凡人修仙传、仙逆...这些真的是国漫之光！每部都追了好几年了，有没有同好？', author: '动漫迷小陈', avatar: 'https://picsum.photos/100/100?random=202', likes: 1863, comments: 563, time: '5小时前', images: ['https://picsum.photos/400/300?random=211'] },
    { id: 3, title: '《繁花》里的上海话听着太上头了', content: '王家卫的《繁花》太上头了，沪语版必须看！胡歌的上海话太苏了，看完想去上海吃排骨年糕...', author: '沪上生活', avatar: 'https://picsum.photos/100/100?random=203', likes: 3215, comments: 1023, time: '1天前', images: [] },
    { id: 4, title: '问一下《流浪地球2》和第一部比怎么样？', content: '第一部看的很震撼，还没看第二部，有没有看过的朋友说说怎么样？值不值得去二刷？', author: '科幻迷阿强', avatar: 'https://picsum.photos/100/100?random=204', likes: 756, comments: 342, time: '3小时前', images: [] },
    { id: 5, title: '今天在家做了红烧肉 分享一下做法', content: '参考了下厨房的配方，五花肉先焯水，炒糖色，加老抽生抽八角桂皮...做出来真的绝了！软糯入味！', author: '美食爱好者', avatar: 'https://picsum.photos/100/100?random=205', likes: 4492, comments: 1587, time: '6小时前', images: ['https://picsum.photos/400/300?random=212', 'https://picsum.photos/400/300?random=213'] },
    { id: 6, title: '《乘风2024》姐姐们的舞台好燃！', content: '这季姐姐都太拼了！每个舞台都好用心，最喜欢的几个舞台都反复看了好几遍。你们pick哪位姐姐？', author: '综艺控', avatar: 'https://picsum.photos/100/100?random=206', likes: 2134, comments: 876, time: '1天前', images: ['https://picsum.photos/400/300?random=214'] },
    { id: 7, title: '有没有人觉得最近短剧特别上头', content: '最近入了短剧的坑，霸道总裁重生什么的一看就停不下来，每次一集就一两分钟，一天能追好几十集...', author: '短剧爱好者', avatar: 'https://picsum.photos/100/100?random=207', likes: 1587, comments: 445, time: '8小时前', images: [] },
    { id: 8, title: '《长相思》涂山璟vs相柳 你站谁？', content: '夭柳党和夭璟党的战争又开始了！来投票，你是站涂山璟还是相柳？我是坚定不移的夭璟党...', author: '小夭最美', avatar: 'https://picsum.photos/100/100?random=208', likes: 5678, comments: 2341, time: '2天前', images: [] },
    { id: 9, title: '勇士这赛季打得真不错', content: '看了勇士打国王的G7，库里还是那个库里！克莱也找回了状态，今年季后赛很有希望啊！', author: '篮球老球迷', avatar: 'https://picsum.photos/100/100?random=209', likes: 987, comments: 432, time: '12小时前', images: [] },
    { id: 10, title: '《凡人修仙传》动漫入坑指南', content: '给没看过原著的朋友一个入坑指南：先看动漫再看小说。动漫做得非常精良，打斗场景燃爆了！韩老魔真的帅！', author: '修仙路人甲', avatar: 'https://picsum.photos/100/100?random=210', likes: 2345, comments: 678, time: '1天前', images: ['https://picsum.photos/400/300?random=215'] },
    { id: 11, title: '芒果TV会员值得开吗？', content: '最近想开个会员追剧，芒果的片库怎么样？和其他平台比有什么优势？求老用户真实体验分享！', author: '新用户小李', avatar: 'https://picsum.photos/100/100?random=211', likes: 678, comments: 345, time: '4小时前', images: [] },
    { id: 12, title: '《斗破苍穹》萧炎最喜欢的几个造型', content: '整理了一下斗破动漫里萧炎的造型变化，从乌坦城少年到炎帝，每一套都好帅！你最喜欢哪一套？', author: '斗破十级学者', avatar: 'https://picsum.photos/100/100?random=212', likes: 1890, comments: 734, time: '2天前', images: ['https://picsum.photos/400/300?random=216', 'https://picsum.photos/400/300?random=217', 'https://picsum.photos/400/300?random=218'] },
    { id: 13, title: '安利一部冷门高分电影《不虚此行》', content: '胡歌演的写悼词的人，很安静很治愈的一部电影。节奏虽慢，但看完心里暖暖的，强烈推荐！', author: '文艺青年小明', avatar: 'https://picsum.photos/100/100?random=213', likes: 1456, comments: 423, time: '3天前', images: [] },
    { id: 14, title: '分享一下我的健身打卡记录', content: '坚持健身三个月，瘦了15斤！每天跟着芒果上的健身视频练半小时，效果真的很明显。大家一起来打卡！', author: '健身达人肥龙', avatar: 'https://picsum.photos/100/100?random=214', likes: 3456, comments: 1023, time: '1天前', images: ['https://picsum.photos/400/300?random=219'] },
    { id: 15, title: '五一假期追剧清单来啦', content: '整理了一份五一假期追剧清单：电视剧《新生》《追风者》，电影《第二十条》《热辣滚烫》，综艺《种地吧》...', author: '剧荒终结者', avatar: 'https://picsum.photos/100/100?random=215', likes: 4123, comments: 1567, time: '5小时前', images: [] }
  ],

  // ==================== 15. 用户信息 ====================
  user: {
    id: 10001,
    nickname: '芒果小粉丝',
    avatar: 'https://picsum.photos/200/200?random=300',
    phone: '138****8888',
    vipLevel: 5,
    vipExpireDate: '2025-06-15',
    balance: 58.80,
    coins: 1250,
    favoritesCount: 86,
    historyCount: 312,
    followingCount: 45,
    followersCount: 128
  },

  // ==================== 16. 搜索建议 ====================
  searchSuggestions: [
    { id: 1, keyword: '狂飙', hot: true, count: '1.2亿' },
    { id: 2, keyword: '繁花', hot: true, count: '9856万' },
    { id: 3, keyword: '斗破苍穹', hot: true, count: '8623万' },
    { id: 4, keyword: '庆余年2', hot: true, count: '7458万' },
    { id: 5, keyword: '长相思', hot: false, count: '6234万' },
    { id: 6, keyword: '热辣滚烫', hot: true, count: '5896万' },
    { id: 7, keyword: '沈腾', hot: false, count: '5123万' },
    { id: 8, keyword: '乘风破浪', hot: true, count: '4678万' },
    { id: 9, keyword: '凡人修仙传', hot: false, count: '4234万' },
    { id: 10, keyword: '狐妖小红娘', hot: false, count: '3987万' },
    { id: 11, keyword: '短剧推荐', hot: true, count: '3546万' },
    { id: 12, keyword: '满江红', hot: false, count: '3125万' },
    { id: 13, keyword: '与凤行', hot: false, count: '2896万' },
    { id: 14, keyword: '大侦探', hot: false, count: '2456万' },
    { id: 15, keyword: '歌手2024', hot: true, count: '2134万' }
  ],

  // ==================== 17. 视频详情 ====================
  videoDetail: {
    id: 1001,
    title: '狂飙',
    cover: 'https://picsum.photos/400/225?random=6',
    poster: 'https://picsum.photos/750/350?random=1',
    score: 9.5,
    year: 2023,
    region: '中国大陆',
    category: '犯罪/剧情/悬疑',
    language: '汉语普通话',
    episodeCount: 39,
    currentEpisode: 1,
    updateStatus: '全39集',
    description: '京海市一线刑警安欣（张译 饰）在与黑恶势力的斗争中，不断遭到保护伞的打击，始终无法将犯罪分子绳之以法。全国政法队伍教育整顿工作开展后，临江省派出指导组入驻京海，联合公检法司各部门，清除了政法队伍内部的腐败分子，粉碎了黑恶势力的保护伞，一举铲除了盘踞京海多年的强盛集团。',
    directors: ['徐纪周'],
    actors: [
      { name: '张译', role: '安欣', avatar: 'https://picsum.photos/200/200?random=101' },
      { name: '张颂文', role: '高启强', avatar: 'https://picsum.photos/200/200?random=102' },
      { name: '李一桐', role: '孟钰', avatar: 'https://picsum.photos/200/200?random=301' },
      { name: '吴刚', role: '徐忠', avatar: 'https://picsum.photos/200/200?random=302' }
    ],
    tags: ['扫黑', '刑侦', '悬疑', '剧情', '高能'],
    views: '128.6亿',
    likes: '3568.2万',
    comments: '1289.5万',
    relatedVideos: [
      { id: 2001, title: '猎冰', cover: 'https://picsum.photos/400/225?random=24', score: 8.4, category: '电视剧', description: '张颂文主演缉毒题材刑侦剧' },
      { id: 2002, title: '隐秘的角落', cover: 'https://picsum.photos/400/225?random=77', score: 9.2, category: '电视剧', description: '三个孩子无意拍下一场谋杀' },
      { id: 2003, title: '漫长的季节', cover: 'https://picsum.photos/400/225?random=73', score: 9.4, category: '电视剧', description: '辛爽导演又一悬疑力作' },
      { id: 2004, title: '扫黑风暴', cover: 'https://picsum.photos/400/225?random=303', score: 8.5, category: '电视剧', description: '孙红雷主演扫黑除恶大剧' },
      { id: 2005, title: '人民的名义', cover: 'https://picsum.photos/400/225?random=304', score: 9.1, category: '电视剧', description: '陆毅张丰毅主演反腐大剧' },
      { id: 2006, title: '破冰行动', cover: 'https://picsum.photos/400/225?random=305', score: 8.6, category: '电视剧', description: '黄景瑜主演缉毒题材剧' }
    ],
    episodes: [
      { ep: 1, title: '第1集', duration: '45:12', url: '/play/1001/1', free: true },
      { ep: 2, title: '第2集', duration: '44:38', url: '/play/1001/2', free: true },
      { ep: 3, title: '第3集', duration: '45:05', url: '/play/1001/3', free: true },
      { ep: 4, title: '第4集', duration: '44:50', url: '/play/1001/4', free: false },
      { ep: 5, title: '第5集', duration: '45:22', url: '/play/1001/5', free: false },
      { ep: 6, title: '第6集', duration: '44:45', url: '/play/1001/6', free: false },
      { ep: 7, title: '第7集', duration: '45:10', url: '/play/1001/7', free: false },
      { ep: 8, title: '第8集', duration: '44:55', url: '/play/1001/8', free: false },
      { ep: 9, title: '第9集', duration: '45:18', url: '/play/1001/9', free: false },
      { ep: 10, title: '第10集', duration: '44:42', url: '/play/1001/10', free: false },
      { ep: 11, title: '第11集', duration: '45:08', url: '/play/1001/11', free: false },
      { ep: 12, title: '第12集', duration: '44:48', url: '/play/1001/12', free: false },
      { ep: 13, title: '第13集', duration: '45:15', url: '/play/1001/13', free: false },
      { ep: 14, title: '第14集', duration: '44:52', url: '/play/1001/14', free: false },
      { ep: 15, title: '第15集', duration: '45:20', url: '/play/1001/15', free: false },
      { ep: 16, title: '第16集', duration: '44:40', url: '/play/1001/16', free: false },
      { ep: 17, title: '第17集', duration: '45:25', url: '/play/1001/17', free: false },
      { ep: 18, title: '第18集', duration: '44:58', url: '/play/1001/18', free: false },
      { ep: 19, title: '第19集', duration: '45:12', url: '/play/1001/19', free: false },
      { ep: 20, title: '第20集', duration: '44:35', url: '/play/1001/20', free: false },
      { ep: 21, title: '第21集', duration: '45:18', url: '/play/1001/21', free: false },
      { ep: 22, title: '第22集', duration: '44:50', url: '/play/1001/22', free: false },
      { ep: 23, title: '第23集', duration: '45:05', url: '/play/1001/23', free: false },
      { ep: 24, title: '第24集', duration: '44:42', url: '/play/1001/24', free: false },
      { ep: 25, title: '第25集', duration: '45:22', url: '/play/1001/25', free: false },
      { ep: 26, title: '第26集', duration: '44:48', url: '/play/1001/26', free: false },
      { ep: 27, title: '第27集', duration: '45:15', url: '/play/1001/27', free: false },
      { ep: 28, title: '第28集', duration: '44:55', url: '/play/1001/28', free: false },
      { ep: 29, title: '第29集', duration: '45:08', url: '/play/1001/29', free: false },
      { ep: 30, title: '第30集', duration: '44:45', url: '/play/1001/30', free: false },
      { ep: 31, title: '第31集', duration: '45:20', url: '/play/1001/31', free: false },
      { ep: 32, title: '第32集', duration: '44:38', url: '/play/1001/32', free: false },
      { ep: 33, title: '第33集', duration: '45:12', url: '/play/1001/33', free: false },
      { ep: 34, title: '第34集', duration: '44:50', url: '/play/1001/34', free: false },
      { ep: 35, title: '第35集', duration: '45:05', url: '/play/1001/35', free: false },
      { ep: 36, title: '第36集', duration: '44:42', url: '/play/1001/36', free: false },
      { ep: 37, title: '第37集', duration: '45:18', url: '/play/1001/37', free: false },
      { ep: 38, title: '第38集', duration: '44:55', url: '/play/1001/38', free: false },
      { ep: 39, title: '第39集（大结局）', duration: '48:30', url: '/play/1001/39', free: false }
    ]
  },

  // ==================== 18. 音频详情 ====================
  audioDetail: {
    id: 1,
    title: '鬼吹灯之精绝古城',
    cover: 'https://picsum.photos/400/225?random=401',
    author: '天下霸唱',
    host: '周建龙',
    category: '悬疑/探险',
    episodes: 120,
    plays: '5623.4万',
    score: 9.6,
    description: '胡八一上山下乡来到云南，在深山老林里发现了一座古墓。从此，他的人生轨迹彻底改变。摸金校尉，合则生、分则死。在这条盗墓寻宝的路上，他与王胖子、Shirley杨组成铁三角，深入大漠精绝古城、龙岭迷窟、云南虫谷、昆仑神宫……历经千难万险，揭开一个又一个千古谜团。',
    relatedAudios: [
      { id: 2, title: '盗墓笔记', cover: 'https://picsum.photos/400/225?random=402', author: '南派三叔', category: '悬疑/探险' },
      { id: 3, title: '茅山后裔', cover: 'https://picsum.photos/400/225?random=403', author: '大力金刚掌', category: '悬疑/灵异' },
      { id: 4, title: '黄河鬼棺', cover: 'https://picsum.photos/400/225?random=404', author: '南派三叔', category: '悬疑/探险' }
    ],
    episodeList: [
      { ep: 1, title: '精绝古城 第一集', duration: '18:25', free: true },
      { ep: 2, title: '精绝古城 第二集', duration: '19:12', free: true },
      { ep: 3, title: '精绝古城 第三集', duration: '18:48', free: true },
      { ep: 4, title: '精绝古城 第四集', duration: '20:05', free: false },
      { ep: 5, title: '精绝古城 第五集', duration: '19:35', free: false },
      { ep: 6, title: '精绝古城 第六集', duration: '18:52', free: false },
      { ep: 7, title: '精绝古城 第七集', duration: '19:18', free: false },
      { ep: 8, title: '精绝古城 第八集', duration: '20:22', free: false },
      { ep: 9, title: '精绝古城 第九集', duration: '19:45', free: false },
      { ep: 10, title: '精绝古城 第十集', duration: '18:58', free: false }
    ]
  },

  // ==================== 19. 论坛帖子详情 ====================
  bbsDetail: {
    id: 1,
    title: '《狂飙》里高启强到底算不算反派？',
    content: '刚追完狂飙，感觉高启强这个角色塑造得太好了。从卖鱼小贩到黑老大，一路走过来，你说他坏吧，他也有情有义，对弟弟妹妹好得没话说，对陈书婷也是真心实意。但你说他是好人吧，他手上沾的血也不少，徐江、李响这些人的死都和他脱不了关系。\n\n其实我觉得这就是《狂飙》最高明的地方——它没有简单地把人物分成好坏两面。高启强是被环境逼成这样的，他的堕落有一个完整的心路历程。最开始只是为了保护家人不被欺负，后来一步步身不由己。每次看他和安欣的对手戏都特别揪心，两个曾经的好朋友，最后走向了完全不同的道路。\n\n你们觉得高启强到底算不算反派？我觉得不能简单用"反派"来定义他，他是一个被时代和命运裹挟的悲剧人物。',
    author: '追剧达人',
    avatar: 'https://picsum.photos/100/100?random=405',
    time: '2小时前',
    likes: 2580,
    comments: 892,
    shares: 345,
    tags: ['狂飙', '剧评', '高启强'],
    replies: [
      { author: '影视观察员', avatar: 'https://picsum.photos/100/100?random=406', content: '说得太对了！高启强就是个悲剧人物，他不是天生的坏人。你看他对高启盛、高启兰多好，对陈书婷也是真心的。但环境一步步把他推向了深渊。', time: '1小时前', likes: 128 },
      { author: '狂飙十级学者', avatar: 'https://picsum.photos/100/100?random=407', content: '我觉得高启强就是反派，只不过是个有血有肉的反派。好人做坏事还是坏事，不能因为他有苦衷就否定他对社会造成的伤害。', time: '45分钟前', likes: 256 },
      { author: '张颂文铁粉', avatar: 'https://picsum.photos/100/100?random=408', content: '张颂文演技封神了！高启强这个角色换别人演可能就成一个脸谱化的黑老大了，但他演出了那种底层人物的辛酸和无奈。', time: '30分钟前', likes: 189 },
      { author: '社会学爱好者', avatar: 'https://picsum.photos/100/100?random=409', content: '从社会学角度看，高启强的悲剧反映了底层群体在社会转型期的困境。正规渠道走不通的时候，有些人就会铤而走险。', time: '20分钟前', likes: 95 },
      { author: '路人甲', avatar: 'https://picsum.photos/100/100?random=410', content: '不管怎么说，害了人就是害了人。剧里好看是一回事，现实生活中这种人必须受到法律制裁。安欣才是真正的英雄！', time: '10分钟前', likes: 312 }
    ]
  },

  // ==================== 20. 合集视频 ====================
  collectionVideos: [
    {
      id: 1,
      collectionName: '古装仙侠必看TOP10',
      cover: 'https://picsum.photos/400/225?random=411',
      count: 10,
      description: '精选10部高分古装仙侠剧，每一部都是口碑与热度双丰收的佳作。从经典到新番，带你领略东方仙侠世界的魅力。',
      list: [
        { title: '长相思', cover: 'https://picsum.photos/400/225?random=412', score: 9.2, desc: '杨紫张晚意领衔，上古神话史诗巨制', tags: ['古装', '爱情', '神话'] },
        { title: '与凤行', cover: 'https://picsum.photos/400/225?random=413', score: 8.8, desc: '赵丽颖林更新再续前缘，仙侠虐恋', tags: ['古装', '仙侠', '爱情'] },
        { title: '狐妖小红娘', cover: 'https://picsum.photos/400/225?random=414', score: 9.1, desc: '国漫改编巅峰之作，竹业篇催泪来袭', tags: ['玄幻', '爱情', '仙侠'] },
        { title: '苍兰诀', cover: 'https://picsum.photos/400/225?random=415', score: 8.9, desc: '虞书欣王鹤棣演绎东方奇幻爱情', tags: ['古装', '奇幻', '爱情'] },
        { title: '沉香如屑', cover: 'https://picsum.photos/400/225?random=416', score: 8.7, desc: '杨紫成毅三世虐恋，仙侠经典', tags: ['古装', '仙侠', '虐恋'] },
        { title: '星汉灿烂', cover: 'https://picsum.photos/400/225?random=417', score: 9.0, desc: '吴磊赵露思演绎乱世家国情怀', tags: ['古装', '爱情', '权谋'] },
        { title: '莲花楼', cover: 'https://picsum.photos/400/225?random=418', score: 9.3, desc: '成毅曾舜晞三人行破案传奇', tags: ['古装', '武侠', '探案'] },
        { title: '玉骨遥', cover: 'https://picsum.photos/400/225?random=419', score: 8.6, desc: '肖战任敏东方奇幻巨制', tags: ['古装', '玄幻', '爱情'] },
        { title: '长月烬明', cover: 'https://picsum.photos/400/225?random=420', score: 8.8, desc: '罗云熙白鹿三世宿命虐恋', tags: ['古装', '仙侠', '奇幻'] },
        { title: '云之羽', cover: 'https://picsum.photos/400/225?random=421', score: 8.5, desc: '虞书欣张凌赫谍战古装新风格', tags: ['古装', '谍战', '爱情'] }
      ]
    }
  ],

  // ==================== 21. 用户记录 ====================
  userRecords: {
    buyRecords: [
      { id: 1, title: '芒果VIP月卡', price: '¥15.00', time: '2024-06-15', status: '已完成', orderNo: 'MG20240615001' },
      { id: 2, title: '芒果VIP季卡', price: '¥42.00', time: '2024-05-20', status: '已完成', orderNo: 'MG20240520002' },
      { id: 3, title: '芒果VIP年卡', price: '¥158.00', time: '2024-03-10', status: '已完成', orderNo: 'MG20240310003' },
      { id: 4, title: 'SVIP超级会员月卡', price: '¥25.00', time: '2024-06-01', status: '已完成', orderNo: 'MG20240601004' },
      { id: 5, title: '芒果TV全屏影视会员', price: '¥198.00', time: '2024-01-15', status: '已过期', orderNo: 'MG20240115005' }
    ],
    unlockRecords: [
      { id: 1, title: '狂飙 第39集', price: '¥3.00', time: '2024-06-10', expireTime: '永久有效' },
      { id: 2, title: '长相思 结局超前点映', price: '¥18.00', time: '2024-05-25', expireTime: '永久有效' },
      { id: 3, title: '繁花 番外篇', price: '¥6.00', time: '2024-04-18', expireTime: '永久有效' }
    ],
    shareRecords: [
      { id: 1, title: '狂飙', to: '微信好友', time: '2024-06-12', reward: '+5金币' },
      { id: 2, title: '繁花', to: '朋友圈', time: '2024-06-08', reward: '+10金币' },
      { id: 3, title: '斗破苍穹年番', to: 'QQ好友', time: '2024-06-05', reward: '+5金币' }
    ],
    downloadRecords: [
      { id: 1, title: '狂飙 第1集', size: '480MB', time: '2024-06-08', status: '已完成' },
      { id: 2, title: '繁花 第3集', size: '520MB', time: '2024-06-05', status: '已完成' },
      { id: 3, title: '热辣滚烫', size: '1.2GB', time: '2024-06-01', status: '已完成' }
    ],
    collectRecords: [
      { id: 1, title: '狂飙', cover: 'https://picsum.photos/400/225?random=422', type: '电视剧', time: '2024-06-01', progress: '已看35/39集' },
      { id: 2, title: '繁花', cover: 'https://picsum.photos/400/225?random=423', type: '电视剧', time: '2024-05-28', progress: '已看28/30集' },
      { id: 3, title: '流浪地球2', cover: 'https://picsum.photos/400/225?random=424', type: '电影', time: '2024-05-20', progress: '已看完' },
      { id: 4, title: '乘风2024', cover: 'https://picsum.photos/400/225?random=425', type: '综艺', time: '2024-05-15', progress: '已看8/12期' },
      { id: 5, title: '斗破苍穹年番', cover: 'https://picsum.photos/400/225?random=426', type: '动漫', time: '2024-05-10', progress: '已看48/52集' }
    ],
    historyRecords: [
      { id: 1, title: '狂飙', cover: 'https://picsum.photos/400/225?random=427', type: '电视剧', time: '2024-06-15 20:30', progress: '第36集 35:42/45:12' },
      { id: 2, title: '繁花', cover: 'https://picsum.photos/400/225?random=428', type: '电视剧', time: '2024-06-15 18:15', progress: '第29集 22:18/44:50' },
      { id: 3, title: '乘风2024', cover: 'https://picsum.photos/400/225?random=429', type: '综艺', time: '2024-06-14 21:00', progress: '第9期 45:30/98:15' },
      { id: 4, title: '热辣滚烫', cover: 'https://picsum.photos/400/225?random=430', type: '电影', time: '2024-06-14 15:45', progress: '1:18:35/2:09:20' },
      { id: 5, title: '斗破苍穹年番', cover: 'https://picsum.photos/400/225?random=431', type: '动漫', time: '2024-06-13 19:30', progress: '第50集 12:05/20:18' }
    ]
  },

  // ==================== 22. 用户设置 ====================
  userAbout: {
    profile: {
      nickname: '芒果小粉丝',
      avatar: 'https://picsum.photos/200/200?random=432',
      gender: '男',
      birthday: '1998-06-15',
      phone: '138****8888',
      email: 'user@mgtv.com',
      region: '广东深圳',
      signature: '好剧追不停'
    },
    problems: [
      { id: 1, title: '如何开通VIP会员？', content: '点击"我的"页面中的"开通会员"，选择您需要的会员类型（月卡/季卡/年卡），确认支付即可。支持微信支付、支付宝、Apple Pay等多种支付方式。' },
      { id: 2, title: '如何下载视频？', content: '在视频播放页面点击下载按钮，选择清晰度即可下载。VIP会员可下载高清及以上清晰度视频。下载的视频在会员有效期内可离线观看。' },
      { id: 3, title: '会员到期后下载的视频还能看吗？', content: '会员到期后，已下载的视频将无法继续观看。建议您在会员有效期内观看完毕，或续费会员继续享受下载观看服务。' },
      { id: 4, title: '如何取消自动续费？', content: '打开"我的"页面→"会员中心"→"管理自动续费"→关闭自动续费。微信支付用户也可在微信支付设置中取消；支付宝用户可在支付宝免密支付中取消。' },
      { id: 5, title: '账号可以在多台设备上登录吗？', content: '同一账号最多可在5台设备上登录，但同时播放的设备限制为2台。如需更多设备同时播放，请升级SVIP超级会员（支持4台设备同时播放）。' },
      { id: 6, title: '如何修改密码？', content: '进入"我的"→"设置"→"账号安全"→"修改密码"，输入原密码和新密码即可。如忘记原密码，可通过绑定的手机号验证重置密码。' },
      { id: 7, title: '视频播放卡顿怎么办？', content: '1. 检查网络连接是否稳定；2. 尝试切换清晰度（如从超清切换到高清）；3. 清理APP缓存：设置→清除缓存；4. 重启APP或重启设备；5. 确认APP已更新到最新版本。' },
      { id: 8, title: '如何联系客服？', content: '在线客服：APP内"我的"→"帮助与反馈"→"在线客服"；客服热线：400-677-0707（9:00-22:00）；微信客服：关注"芒果TV会员"公众号留言。' }
    ],
    themes: [
      { id: 1, name: '深色模式', icon: '🌙', active: true },
      { id: 2, name: '浅色模式', icon: '☀️', active: false }
    ]
  },

  // ==================== 23. 小说阅读内容 ====================
  novelReaderContent: {
    id: 1,
    novelTitle: '斗破苍穹',
    chapterTitle: '第一章 陨落的天才',
    content: '　　"斗之力，三段！"\n\n　　望着测验魔石碑上面闪亮得甚至有些刺眼的五个大字，少年面无表情，唇角有着一抹自嘲，紧握的手掌，因为大力，而导致略微尖锐的指甲深深的刺进了掌心之中，带来一阵阵钻心的疼痛…\n\n　　"萧炎，斗之力，三段！级别：低级！"测验魔石碑之旁，一位中年男子，看了一眼碑上所显示出来的信息，语气漠然的将之公布了出来…\n\n　　中年男子话刚刚脱口，便是不出意外的在人头汹涌的广场上带起了一阵嘲讽的骚动。\n\n　　"三段？嘿嘿，果然不出我所料，这个"天才"这一年又是在原地踏步！"\n\n　　"哎，这废物真是把家族的脸都给丢光了。"\n\n　　"要不是族长是他的父亲，这种废物，早就被驱赶出家族，任其自生自灭了，哪还有机会待在家族中白吃白喝。"\n\n　　"唉，昔年那名闻乌坦城的天才少年，如今怎么落魄成这般模样了啊？"\n\n　　"谁知道呢，或许做了什么亏心事，惹得神灵降怒了吧…"\n\n　　周围传来的不屑嘲笑以及惋惜轻叹，落在那如木桩待在原地的少年耳中，恍如一根根利刺狠狠的扎在心脏一般，让得少年呼吸微微急促。\n\n　　少年缓缓抬起头来，露出一张有些清秀的稚嫩脸庞，漆黑的眸子木然的在周围那些嘲讽的同龄人身上扫过，少年嘴角的自嘲，似乎变得更加苦涩了。\n\n　　"这些人，都如此刻薄势力吗？或许是因为三年前他们曾经在自己面前露出过最谦卑的笑容，所以，如今想要讨还回去吧…"苦笑着摇了摇头，萧炎落寞的转身，安静的回到了队伍的最后一排，孤单的身影，与周围的世界，有些格格不入。\n\n　　萧炎，萧家历史上空前绝后的斗气修炼天才。四岁练气，十岁拥有九段斗之气，十一岁突破十段斗之气，成功凝聚斗之气旋，一跃成为家族百年之内最年轻的斗者！然而三年之前，这名声望达到巅峰的天才少年，却是突兀的接受到了有生以来最残酷的打击——辛辛苦苦修炼十数载方才凝聚的斗之气旋，一夜之间，化为乌有。斗之气也在不断的诡异减少，从斗者降到了如今的斗之力三段。整整三年时间，家族冷落，旁人嘲讽，未婚妻退婚…种种打击接踵而至。\n\n　　就在他即将绝望的时候，一缕灵魂从他手上的戒指里浮现，一道苍老的声音响起："小家伙，想知道你的斗气为什么会消失吗？"\n\n　　萧炎猛然抬头，眼中闪过一丝震惊与希望…',
    prevChapter: '序章',
    nextChapter: '第二章 斗气大陆'
  },

  // ==================== 24. 茶帖 ====================
  teaPosts: [
    { id: 1, title: '今天喝了什么茶？', content: '西湖龙井，明前新茶，茶汤清澈透亮，入口甘甜顺滑，回味悠长。果然是一分钱一分货，好茶就是不一样！', author: '茶友小王', avatar: 'https://picsum.photos/100/100?random=433', time: '2小时前', likes: 56, comments: 12 },
    { id: 2, title: '分享一下我的茶具收藏', content: '入坑茶道三年，陆陆续续收了五六把紫砂壶，还有几套青花瓷盖碗。最喜欢这把朱泥西施壶，泡熟普绝了！', author: '紫砂壶爱好者', avatar: 'https://picsum.photos/100/100?random=434', time: '5小时前', likes: 128, comments: 35 },
    { id: 3, title: '武夷岩茶入门指南', content: '新手喝岩茶建议从肉桂入手，香气高扬辨识度高。大红袍水太深，市面上的商品大红袍基本都是拼配的，纯种大红袍可遇不可求。', author: '岩茶老饕', avatar: 'https://picsum.photos/100/100?random=435', time: '8小时前', likes: 89, comments: 23 },
    { id: 4, title: '办公室简易泡茶方案', content: '打工人也要喝茶！推荐飘逸杯+挂耳茶包，方便快捷不占地方。我这周喝的是安溪铁观音，兰花香浓郁，提神醒脑效果特别好。', author: '打工茶人', avatar: 'https://picsum.photos/100/100?random=436', time: '1天前', likes: 203, comments: 56 },
    { id: 5, title: '普洱生茶和熟茶怎么选？', content: '新手推荐先喝熟普，口感温和不刺激。生普新茶性寒，肠胃不好的慎重。我个人偏爱大益7572，经典口粮茶，性价比极高。', author: '普洱迷', avatar: 'https://picsum.photos/100/100?random=437', time: '1天前', likes: 167, comments: 42 }
  ],

  // ==================== 25. 微密数据 ====================
  weimiData: {
    id: 1,
    name: '微密圈精选',
    cover: 'https://picsum.photos/400/225?random=438',
    description: '精选优质短视频合集，每日更新热门内容，涵盖搞笑、美食、旅行、萌宠等多个品类，让你刷到停不下来！',
    videos: [
      { title: '猫咪大战扫地机器人', cover: 'https://picsum.photos/400/225?random=439', duration: '02:15', views: '12.3万' },
      { title: '一分钟学会做提拉米苏', cover: 'https://picsum.photos/400/225?random=440', duration: '01:08', views: '8.7万' },
      { title: '川西自驾游绝美路线', cover: 'https://picsum.photos/400/225?random=441', duration: '03:42', views: '25.6万' },
      { title: '街舞大神炸裂现场', cover: 'https://picsum.photos/400/225?random=442', duration: '01:55', views: '18.9万' },
      { title: '萌娃模仿爸爸打电话', cover: 'https://picsum.photos/400/225?random=443', duration: '00:48', views: '35.2万' },
      { title: '创意折纸手工教程', cover: 'https://picsum.photos/400/225?random=444', duration: '04:20', views: '6.5万' }
    ]
  }
};
