/**
 * 芒果TV H5 视频网站 — 应用主逻辑
 * @author Wujun
 */
(function () {
  'use strict';

  const M = window.MockData;

  // ==================== 页面模板 ====================

  const PAGE_TEMPLATES = {
    'page-home': `
      <div class="header">
        <div class="header-left"><span class="header-logo-text">芒果TV</span></div>
        <div class="search-bar" data-navigate="search">
          <span class="search-icon">🔍</span>
          <span class="search-input" style="color:var(--text-placeholder);">搜索视频、明星、短剧</span>
        </div>
        <div class="header-right">
          <span class="header-icon" data-navigate="messages">📬<span class="badge-dot"></span></span>
        </div>
      </div>
      <div class="page-content">
        <div class="banner">
          <div class="banner-wrapper"></div>
          <div class="banner-dots"></div>
        </div>
        <div class="category-grid"></div>
        <div class="section">
          <div class="section-header">
            <span class="section-title">热播排行</span>
            <span class="section-more" data-navigate="video-list/hot">更多 ›</span>
          </div>
          <div class="rank-list"></div>
        </div>
        <div class="section">
          <div class="section-header">
            <span class="section-title">为你推荐</span>
            <span class="section-more" data-navigate="video-list/recommend">更多 ›</span>
          </div>
          <div class="video-grid" id="home-recommend-grid"></div>
        </div>
        <div class="section">
          <div class="section-header">
            <span class="section-title">热门短剧</span>
            <span class="section-more" data-navigate="short-drama">更多 ›</span>
          </div>
          <div class="hscroll" id="home-shortdrama-scroll"></div>
        </div>
        <div class="section">
          <div class="section-header">
            <span class="section-title">热门综艺</span>
            <span class="section-more" data-navigate="video-list/variety">更多 ›</span>
          </div>
          <div class="hscroll" id="home-variety-scroll"></div>
        </div>
        <div class="section">
          <div class="section-header">
            <span class="section-title">热门电影</span>
            <span class="section-more" data-navigate="video-list/movie">更多 ›</span>
          </div>
          <div class="hscroll" id="home-movie-scroll"></div>
        </div>
      </div>`,

    'page-short-drama': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center">热门短剧</div>
        <div class="header-right"><span class="header-icon" data-navigate="search">🔍</span></div>
      </div>
      <div class="tabs" id="short-drama-tabs">
        <span class="tab active">推荐</span>
        <span class="tab">最热</span>
        <span class="tab">最新</span>
        <span class="tab">古装</span>
        <span class="tab">都市</span>
      </div>
      <div class="page-content">
        <div class="video-grid" id="short-drama-grid"></div>
      </div>`,

    'page-anime': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center">动漫专区</div>
        <div class="header-right"><span class="header-icon" data-navigate="search">🔍</span></div>
      </div>
      <div class="tabs" id="anime-tabs">
        <span class="tab active">推荐</span>
        <span class="tab">玄幻</span>
        <span class="tab">修真</span>
        <span class="tab">武侠</span>
        <span class="tab">电竞</span>
      </div>
      <div class="page-content">
        <div class="video-grid" id="anime-grid"></div>
      </div>`,

    'page-novel': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center">小说专区</div>
        <div class="header-right"><span class="header-icon" data-navigate="search">🔍</span></div>
      </div>
      <div class="tabs" id="novel-tabs">
        <span class="tab active">推荐</span>
        <span class="tab">玄幻</span>
        <span class="tab">仙侠</span>
        <span class="tab">都市</span>
        <span class="tab">游戏</span>
      </div>
      <div class="page-content">
        <div class="video-grid" id="novel-grid"></div>
      </div>`,

    'page-user': `
      <div class="user-page">
        <div class="user-header-bg">
          <div class="user-decor"></div>
          <div class="user-info-row">
            <div class="user-avatar"><img id="user-avatar-img" src="" alt=""></div>
            <div>
              <div class="user-name-row">
                <span class="user-nickname" id="user-nickname"></span>
                <span class="user-vip-badge" id="user-vip-badge"></span>
              </div>
              <div class="user-desc" id="user-desc"></div>
            </div>
          </div>
        </div>
        <div class="user-stats">
          <div class="user-stat-item" data-navigate="home-collect"><span class="user-stat-num" id="user-favorites"></span><span class="user-stat-label">收藏</span></div>
          <div class="user-stat-item" data-navigate="home-history"><span class="user-stat-num" id="user-history"></span><span class="user-stat-label">历史</span></div>
          <div class="user-stat-item"><span class="user-stat-num" id="user-following"></span><span class="user-stat-label">关注</span></div>
          <div class="user-stat-item"><span class="user-stat-num" id="user-followers"></span><span class="user-stat-label">粉丝</span></div>
        </div>
        <div class="vip-card">
          <div class="vip-logo">芒果VIP会员</div>
          <div class="vip-benefits">解锁专属片库 · 无广告 · 高清画质 · 追剧特权</div>
          <span class="vip-btn" data-navigate="vip">立即开通</span>
          <div class="vip-decor">👑</div>
        </div>
        <div class="menu-group">
          <div class="menu-item" data-navigate="home-collect"><span class="menu-icon accent">⭐</span><span class="menu-text">我的收藏</span><span class="menu-badge" id="menu-favorites-count"></span><span class="menu-arrow">›</span></div>
          <div class="menu-item" data-navigate="home-history"><span class="menu-icon">🕐</span><span class="menu-text">观看历史</span><span class="menu-arrow">›</span></div>
          <div class="menu-item" data-navigate="myPost"><span class="menu-icon">📝</span><span class="menu-text">我的帖子</span><span class="menu-arrow">›</span></div>
          <div class="menu-item" data-navigate="download"><span class="menu-icon">📥</span><span class="menu-text">离线下载</span><span class="menu-arrow">›</span></div>
          <div class="menu-item" data-navigate="buyRecord"><span class="menu-icon">📋</span><span class="menu-text">购买记录</span><span class="menu-arrow">›</span></div>
          <div class="menu-item" data-navigate="unlockRecords"><span class="menu-icon">🔓</span><span class="menu-text">解锁记录</span><span class="menu-arrow">›</span></div>
          <div class="menu-item" data-navigate="shareRecord"><span class="menu-icon">📤</span><span class="menu-text">分享记录</span><span class="menu-arrow">›</span></div>
        </div>
        <div class="menu-group">
          <div class="menu-item" data-navigate="myFavoritesNovels"><span class="menu-icon">📖</span><span class="menu-text">收藏小说</span><span class="menu-arrow">›</span></div>
          <div class="menu-item" data-navigate="myAudioFavorites"><span class="menu-icon">🎧</span><span class="menu-text">收藏音频</span><span class="menu-arrow">›</span></div>
          <div class="menu-item" data-navigate="myCommicCollection"><span class="menu-icon">🐉</span><span class="menu-text">收藏动漫</span><span class="menu-arrow">›</span></div>
        </div>
        <div class="menu-group">
          <div class="menu-item" data-navigate="bbs"><span class="menu-icon accent">💬</span><span class="menu-text">社区论坛</span><span class="menu-red-dot"></span><span class="menu-arrow">›</span></div>
          <div class="menu-item" data-navigate="tea"><span class="menu-icon">🍵</span><span class="menu-text">茶社</span><span class="menu-arrow">›</span></div>
          <div class="menu-item" data-navigate="messages"><span class="menu-icon">📬</span><span class="menu-text">我的消息</span><span class="menu-arrow">›</span></div>
        </div>
        <div class="menu-group">
          <div class="menu-item" data-navigate="account"><span class="menu-icon">💰</span><span class="menu-text">我的账户</span><span class="menu-arrow">›</span></div>
          <div class="menu-item" data-navigate="changeInfo"><span class="menu-icon">👤</span><span class="menu-text">修改资料</span><span class="menu-arrow">›</span></div>
          <div class="menu-item" data-navigate="changePassword"><span class="menu-icon">🔒</span><span class="menu-text">修改密码</span><span class="menu-arrow">›</span></div>
          <div class="menu-item" data-navigate="problem"><span class="menu-icon">❓</span><span class="menu-text">帮助中心</span><span class="menu-arrow">›</span></div>
          <div class="menu-item" data-navigate="theme"><span class="menu-icon">🎨</span><span class="menu-text">主题设置</span><span class="menu-arrow">›</span></div>
        </div>
        <div class="menu-group">
          <div class="menu-item" data-navigate="settings"><span class="menu-icon">⚙️</span><span class="menu-text">设置</span><span class="menu-arrow">›</span></div>
          <div class="menu-item" data-navigate="pages"><span class="menu-icon">📂</span><span class="menu-text">全部页面</span><span class="menu-arrow">›</span></div>
          <div class="menu-item" data-navigate="spare"><span class="menu-icon">📄</span><span class="menu-text">备用页面</span><span class="menu-arrow">›</span></div>
        </div>
      </div>`,

    'page-video-play': `
      <div class="player-container" style="padding-top:56.25%;position:relative;">
        <div class="player-video" style="background:#000;display:flex;align-items:center;justify-content:center;color:#fff;font-size:48px;">▶</div>
        <div class="player-close" data-back>✕</div>
        <div class="player-controls">
          <div class="player-progress"><div class="player-progress-bar" style="width:0%"></div></div>
          <div class="player-buttons">
            <div class="player-left">
              <span class="player-btn">⏪</span>
              <span class="player-time" id="player-current-time">00:00</span>
            </div>
            <div class="player-center">
              <span class="player-btn play-btn" id="player-play-btn">▶</span>
            </div>
            <div class="player-right">
              <span class="player-time" id="player-total-time">00:00</span>
              <span class="player-btn">⏩</span>
            </div>
          </div>
        </div>
      </div>
      <div class="video-info-section">
        <div class="video-title" id="video-detail-title"></div>
        <div class="video-meta">
          <span class="meta-score" id="video-detail-score"></span>
          <span id="video-detail-year"></span>
          <span id="video-detail-views"></span>
        </div>
        <div class="video-tags-row" id="video-detail-tags"></div>
        <div class="video-desc" id="video-detail-desc"></div>
      </div>
      <div class="divider-thick"></div>
      <div class="episode-section" style="padding:var(--space-lg);">
        <div class="episode-title">选集</div>
        <div class="episode-grid" id="episode-grid"></div>
      </div>
      <div class="divider-thick"></div>
      <div class="section" id="actor-section" style="display:none;">
        <div class="section-header"><span class="section-title">演员</span></div>
        <div class="hscroll" id="actor-scroll"></div>
      </div>
      <div class="section">
        <div class="section-header"><span class="section-title">相关推荐</span></div>
        <div class="hscroll" id="related-video-scroll"></div>
      </div>`,

    'page-search': `
      <div class="search-header-fixed">
        <span class="header-icon" data-back>←</span>
        <div class="search-input-wrap">
          <span class="search-icon">🔍</span>
          <input type="text" id="search-input" placeholder="搜索视频、明星、短剧" autocomplete="off">
          <span class="search-clear" id="search-clear">✕</span>
        </div>
        <span class="search-cancel" id="search-cancel-btn">取消</span>
      </div>
      <div class="search-page">
        <div class="search-hot" id="search-hot-section">
          <div class="search-hot-title">🔥 热门搜索</div>
          <div class="search-hot-tags" id="search-hot-tags"></div>
        </div>
        <div class="search-history" id="search-history-section">
          <div class="search-history-header">
            <span class="title">🕐 搜索历史</span>
            <span class="clear-btn" id="search-history-clear">清空</span>
          </div>
          <div class="search-history-list" id="search-history-list"></div>
        </div>
        <div class="search-results" id="search-results-section" style="display:none;"></div>
      </div>`,

    'page-video-list': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center" id="video-list-title">视频列表</div>
        <div class="header-right"><span class="header-icon" data-navigate="search">🔍</span></div>
      </div>
      <div class="page-content">
        <div class="video-grid" id="video-list-grid"></div>
      </div>`,

    'page-actor-detail': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center" id="actor-name">演员详情</div>
        <div class="header-right"></div>
      </div>
      <div class="page-content" style="padding:var(--space-lg);">
        <div style="display:flex;gap:var(--space-lg);margin-bottom:var(--space-xl);">
          <img id="actor-avatar" src="" style="width:100px;height:100px;border-radius:50%;object-fit:cover;background:var(--bg-card);">
          <div>
            <div style="font-size:var(--font-size-xl);font-weight:700;margin-bottom:var(--space-sm);" id="actor-detail-name"></div>
            <div style="font-size:var(--font-size-sm);color:var(--text-secondary);line-height:1.6;" id="actor-desc"></div>
          </div>
        </div>
        <div class="section-header" style="padding:0 0 var(--space-md) 0;"><span class="section-title">相关作品</span></div>
        <div class="video-grid" id="actor-video-grid"></div>
      </div>`,

    'page-bbs': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center">社区论坛</div>
        <div class="header-right"><span class="header-icon">✏️</span></div>
      </div>
      <div class="tabs" id="bbs-tabs">
        <span class="tab active">热门</span>
        <span class="tab">最新</span>
        <span class="tab">追剧</span>
        <span class="tab">动漫</span>
        <span class="tab">综艺</span>
      </div>
      <div class="page-content" id="bbs-post-list"></div>`,

    'page-collections': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center">我的收藏</div>
        <div class="header-right"></div>
      </div>
      <div class="page-content">
        <div class="video-grid" id="collections-grid"></div>
      </div>`,

    'page-messages': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center">我的消息</div>
        <div class="header-right"></div>
      </div>
      <div class="page-content" id="messages-list"></div>`,

    'page-settings': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center">设置</div>
        <div class="header-right"></div>
      </div>
      <div class="page-content">
        <div class="menu-group" style="margin-top:var(--space-lg);">
          <div class="menu-item" data-navigate="changeInfo"><span class="menu-icon">👤</span><span class="menu-text">个人信息</span><span class="menu-arrow">›</span></div>
          <div class="menu-item" data-navigate="changePassword"><span class="menu-icon">🔒</span><span class="menu-text">账号安全</span><span class="menu-arrow">›</span></div>
          <div class="menu-item" data-navigate="theme"><span class="menu-icon">🎨</span><span class="menu-text">主题设置</span><span class="menu-badge">深色</span><span class="menu-arrow">›</span></div>
        </div>
        <div class="menu-group">
          <div class="menu-item" data-navigate="account"><span class="menu-icon">💰</span><span class="menu-text">我的账户</span><span class="menu-arrow">›</span></div>
          <div class="menu-item" data-navigate="accountChange"><span class="menu-icon">📋</span><span class="menu-text">账户明细</span><span class="menu-arrow">›</span></div>
          <div class="menu-item" data-navigate="download"><span class="menu-icon">📥</span><span class="menu-text">离线下载</span><span class="menu-arrow">›</span></div>
        </div>
        <div class="menu-group">
          <div class="menu-item"><span class="menu-icon">📡</span><span class="menu-text">播放设置</span><span class="menu-arrow">›</span></div>
          <div class="menu-item"><span class="menu-icon">💾</span><span class="menu-text">清除缓存</span><span class="menu-badge">128MB</span><span class="menu-arrow">›</span></div>
          <div class="menu-item" data-navigate="problem"><span class="menu-icon">ℹ️</span><span class="menu-text">帮助中心</span><span class="menu-arrow">›</span></div>
        </div>
        <div style="padding:var(--space-xl) var(--space-lg);">
          <div class="login-btn outline" data-navigate="login" style="display:block;text-align:center;">切换账号</div>
        </div>
      </div>`,

    'page-vip': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center">芒果VIP</div>
        <div class="header-right"></div>
      </div>
      <div class="page-content" style="padding:var(--space-lg);">
        <div class="vip-card" style="margin:0;margin-bottom:var(--space-lg);">
          <div class="vip-logo">👑 芒果VIP会员</div>
          <div class="vip-benefits">尊享会员专属片库、无广告观看、1080P高清画质、杜比音效、多端同步</div>
          <span class="vip-btn" id="vip-open-btn">立即开通 ¥15/月</span>
          <div class="vip-decor">👑</div>
        </div>
        <div class="menu-group" style="margin:0;">
          <div class="menu-item"><span class="menu-icon accent">🎬</span><span class="menu-text">会员专属片库</span><span class="menu-arrow">›</span></div>
          <div class="menu-item"><span class="menu-icon accent">📺</span><span class="menu-text">抢先看剧集</span><span class="menu-arrow">›</span></div>
          <div class="menu-item"><span class="menu-icon accent">🎵</span><span class="menu-text">杜比音效</span><span class="menu-arrow">›</span></div>
          <div class="menu-item"><span class="menu-icon accent">📱</span><span class="menu-text">多端同步观看</span><span class="menu-arrow">›</span></div>
        </div>
      </div>`,

    'page-novel-reader': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center" id="novel-reader-title">阅读</div>
        <div class="header-right"></div>
      </div>
      <div class="page-content" style="padding:var(--space-xl);line-height:2;font-size:var(--font-size-lg);color:var(--text-primary);" id="novel-reader-content">
      </div>`,

    'page-login': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center">登录</div>
        <div class="header-right"></div>
      </div>
      <div class="login-page" style="min-height:calc(100vh - var(--header-height) - var(--safe-area-top));padding-top:60px;">
        <div class="login-logo">芒果TV</div>
        <div class="login-desc">看见好时光</div>
        <div class="login-btn" id="login-phone-btn">📱 手机号登录</div>
        <div class="login-btn outline" id="login-wechat-btn">💬 微信登录</div>
        <div class="login-agreement">登录即表示同意 <a href="#">用户协议</a> 和 <a href="#">隐私政策</a></div>
      </div>`,

    'page-audio': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center">有声书</div>
        <div class="header-right"><span class="header-icon" data-navigate="search">🔍</span></div>
      </div>
      <div class="tabs" id="audio-tabs">
        <span class="tab active">推荐</span>
        <span class="tab">悬疑</span>
        <span class="tab">历史</span>
        <span class="tab">科幻</span>
        <span class="tab">文学</span>
        <span class="tab">相声</span>
      </div>
      <div class="page-content">
        <div class="video-grid" id="audio-grid"></div>
      </div>`,

    'page-audio-detail': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center" id="audio-detail-title">音频详情</div>
        <div class="header-right"></div>
      </div>
      <div class="page-content" style="padding:var(--space-lg);">
        <div style="display:flex;gap:var(--space-lg);margin-bottom:var(--space-xl);">
          <img id="audio-detail-cover" src="" style="width:100px;height:100px;border-radius:var(--radius-md);object-fit:cover;background:var(--bg-card);">
          <div>
            <div style="font-size:var(--font-size-xl);font-weight:700;margin-bottom:var(--space-sm);" id="audio-detail-name"></div>
            <div style="font-size:var(--font-size-sm);color:var(--text-secondary);" id="audio-detail-author"></div>
            <div style="font-size:var(--font-size-sm);color:var(--text-secondary);" id="audio-detail-host"></div>
            <div style="font-size:var(--font-size-sm);color:var(--text-secondary);margin-top:var(--space-sm);" id="audio-detail-meta"></div>
          </div>
        </div>
        <div class="section-header" style="padding:0 0 var(--space-md) 0;"><span class="section-title">节目列表</span></div>
        <div id="audio-episode-list"></div>
        <div class="divider-thick"></div>
        <div style="padding:var(--space-md) 0;" id="audio-detail-desc"></div>
      </div>`,

    'page-audio-rank': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center">有声书排行</div>
        <div class="header-right"></div>
      </div>
      <div class="page-content">
        <div class="rank-list" id="audio-rank-list"></div>
      </div>`,

    'page-allDramas': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center">全部剧集</div>
        <div class="header-right"><span class="header-icon" data-navigate="search">🔍</span></div>
      </div>
      <div class="tabs" id="all-dramas-tabs">
        <span class="tab active">电视剧</span>
        <span class="tab">电影</span>
        <span class="tab">综艺</span>
        <span class="tab">动漫</span>
        <span class="tab">纪录</span>
      </div>
      <div class="page-content">
        <div class="video-grid" id="all-dramas-grid"></div>
      </div>`,

    'page-longVideo': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center">长视频</div>
        <div class="header-right"><span class="header-icon" data-navigate="search">🔍</span></div>
      </div>
      <div class="tabs" id="long-video-tabs">
        <span class="tab active">热门</span>
        <span class="tab">最新</span>
        <span class="tab">电影</span>
        <span class="tab">剧集</span>
      </div>
      <div class="page-content">
        <div class="video-grid" id="long-video-grid"></div>
      </div>`,

    'page-shortVideo': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center">短视频</div>
        <div class="header-right"><span class="header-icon" data-navigate="search">🔍</span></div>
      </div>
      <div class="tabs" id="short-video-tabs">
        <span class="tab active">推荐</span>
        <span class="tab">搞笑</span>
        <span class="tab">美食</span>
        <span class="tab">旅游</span>
        <span class="tab">音乐</span>
      </div>
      <div class="page-content">
        <div class="video-grid" id="short-video-grid"></div>
      </div>`,

    'page-shortList': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center">短剧大全</div>
        <div class="header-right"><span class="header-icon" data-navigate="search">🔍</span></div>
      </div>
      <div class="page-content">
        <div class="video-grid" id="short-list-grid"></div>
      </div>`,

    'page-hotVideo': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center">热门视频</div>
        <div class="header-right"><span class="header-icon" data-navigate="search">🔍</span></div>
      </div>
      <div class="tabs" id="hot-video-tabs">
        <span class="tab active">今日</span>
        <span class="tab">本周</span>
        <span class="tab">本月</span>
      </div>
      <div class="page-content">
        <div class="video-grid" id="hot-video-grid"></div>
      </div>`,

    'page-elites': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center">精选推荐</div>
        <div class="header-right"><span class="header-icon" data-navigate="search">🔍</span></div>
      </div>
      <div class="page-content">
        <div class="video-grid" id="elites-grid"></div>
      </div>`,

    'page-disclose': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center">揭秘</div>
        <div class="header-right"><span class="header-icon" data-navigate="search">🔍</span></div>
      </div>
      <div class="page-content">
        <div class="video-grid" id="disclose-grid"></div>
      </div>`,

    'page-overseasNews': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center">海外资讯</div>
        <div class="header-right"><span class="header-icon" data-navigate="search">🔍</span></div>
      </div>
      <div class="page-content">
        <div class="video-grid" id="overseas-news-grid"></div>
      </div>`,

    'page-bbs-detail': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center">帖子详情</div>
        <div class="header-right"></div>
      </div>
      <div class="page-content" style="padding:var(--space-lg);">
        <div id="bbs-detail-post"></div>
        <div class="divider-thick"></div>
        <div style="font-size:var(--font-size-lg);font-weight:600;padding:var(--space-md) 0;">全部回复</div>
        <div id="bbs-detail-replies"></div>
      </div>`,

    'page-addPost': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center">发帖</div>
        <div class="header-right"><span class="header-icon" id="submit-post-btn" style="color:var(--color-primary);">发布</span></div>
      </div>
      <div class="page-content" style="padding:var(--space-lg);">
        <input type="text" id="add-post-title" placeholder="请输入标题" style="width:100%;padding:var(--space-md);border:1px solid var(--border-color);border-radius:var(--radius-md);font-size:var(--font-size-md);margin-bottom:var(--space-md);background:var(--bg-card);color:var(--text-primary);box-sizing:border-box;">
        <textarea id="add-post-content" placeholder="请输入内容" style="width:100%;height:200px;padding:var(--space-md);border:1px solid var(--border-color);border-radius:var(--radius-md);font-size:var(--font-size-md);resize:none;background:var(--bg-card);color:var(--text-primary);box-sizing:border-box;"></textarea>
      </div>`,

    'page-comic-category': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center">动漫分类</div>
        <div class="header-right"><span class="header-icon" data-navigate="search">🔍</span></div>
      </div>
      <div class="tabs" id="comic-category-tabs">
        <span class="tab active">全部</span>
        <span class="tab">国漫</span>
        <span class="tab">日漫</span>
        <span class="tab">剧场版</span>
        <span class="tab">OVA</span>
      </div>
      <div class="page-content">
        <div class="video-grid" id="comic-category-grid"></div>
      </div>`,

    'page-comic-detail': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center" id="comic-detail-title">动漫详情</div>
        <div class="header-right"></div>
      </div>
      <div class="page-content" style="padding:var(--space-lg);">
        <div style="display:flex;gap:var(--space-lg);margin-bottom:var(--space-xl);">
          <img id="comic-detail-cover" src="" style="width:100px;height:130px;border-radius:var(--radius-md);object-fit:cover;background:var(--bg-card);">
          <div>
            <div style="font-size:var(--font-size-xl);font-weight:700;margin-bottom:var(--space-sm);" id="comic-detail-name"></div>
            <div style="font-size:var(--font-size-sm);color:var(--text-secondary);" id="comic-detail-author"></div>
            <div style="font-size:var(--font-size-sm);color:var(--text-secondary);" id="comic-detail-status"></div>
            <div style="font-size:var(--font-size-sm);color:var(--text-secondary);margin-top:var(--space-sm);" id="comic-detail-latest"></div>
          </div>
        </div>
        <div style="color:var(--text-secondary);line-height:1.8;margin-bottom:var(--space-xl);" id="comic-detail-desc"></div>
        <div class="section-header" style="padding:0 0 var(--space-md) 0;"><span class="section-title">章节列表</span></div>
        <div id="comic-chapter-list"></div>
      </div>`,

    'page-comic-rank': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center">动漫排行</div>
        <div class="header-right"></div>
      </div>
      <div class="page-content">
        <div class="rank-list" id="comic-rank-list"></div>
      </div>`,

    'page-novel-category': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center">小说分类</div>
        <div class="header-right"><span class="header-icon" data-navigate="search">🔍</span></div>
      </div>
      <div class="tabs" id="novel-category-tabs">
        <span class="tab active">全部</span>
        <span class="tab">玄幻</span>
        <span class="tab">仙侠</span>
        <span class="tab">都市</span>
        <span class="tab">言情</span>
        <span class="tab">游戏</span>
      </div>
      <div class="page-content">
        <div class="video-grid" id="novel-category-grid"></div>
      </div>`,

    'page-novel-detail': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center" id="novel-detail-title">小说详情</div>
        <div class="header-right"></div>
      </div>
      <div class="page-content" style="padding:var(--space-lg);">
        <div style="display:flex;gap:var(--space-lg);margin-bottom:var(--space-xl);">
          <img id="novel-detail-cover" src="" style="width:100px;height:130px;border-radius:var(--radius-md);object-fit:cover;background:var(--bg-card);">
          <div>
            <div style="font-size:var(--font-size-xl);font-weight:700;margin-bottom:var(--space-sm);" id="novel-detail-name"></div>
            <div style="font-size:var(--font-size-sm);color:var(--text-secondary);" id="novel-detail-author"></div>
            <div style="font-size:var(--font-size-sm);color:var(--text-secondary);" id="novel-detail-category"></div>
            <div style="font-size:var(--font-size-sm);color:var(--text-secondary);margin-top:var(--space-sm);" id="novel-detail-words"></div>
            <div style="font-size:var(--font-size-sm);color:var(--text-secondary);" id="novel-detail-status"></div>
          </div>
        </div>
        <div style="color:var(--text-secondary);line-height:1.8;margin-bottom:var(--space-xl);" id="novel-detail-desc"></div>
        <div style="display:flex;gap:var(--space-md);margin-bottom:var(--space-lg);">
          <div class="login-btn" data-navigate="chapters" style="flex:1;text-align:center;">开始阅读</div>
        </div>
        <div class="section-header" style="padding:0 0 var(--space-md) 0;"><span class="section-title">章节目录</span></div>
        <div id="novel-detail-chapter-list"></div>
      </div>`,

    'page-novel-rank': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center">小说排行</div>
        <div class="header-right"></div>
      </div>
      <div class="page-content">
        <div class="rank-list" id="novel-rank-list"></div>
      </div>`,

    'page-chapters': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center" id="chapters-novel-title">目录</div>
        <div class="header-right"></div>
      </div>
      <div class="page-content" id="chapters-list"></div>`,

    'page-read': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center" id="read-novel-title">阅读</div>
        <div class="header-right"></div>
      </div>
      <div class="page-content" style="padding:var(--space-xl);line-height:2;font-size:var(--font-size-lg);color:var(--text-primary);" id="read-content">
      </div>`,

    'page-collection-detail': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center">合集详情</div>
        <div class="header-right"></div>
      </div>
      <div class="page-content" style="padding:var(--space-lg);">
        <div id="collection-detail-info"></div>
        <div class="section-header" style="padding:var(--space-md) 0;"><span class="section-title">视频列表</span></div>
        <div class="video-grid" id="collection-detail-grid"></div>
      </div>`,

    'page-collection-videos': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center" id="collection-videos-title">合集视频</div>
        <div class="header-right"></div>
      </div>
      <div class="page-content">
        <div class="video-grid" id="collection-videos-grid"></div>
      </div>`,

    'page-account': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center">我的账户</div>
        <div class="header-right"></div>
      </div>
      <div class="page-content" style="padding:var(--space-lg);">
        <div style="text-align:center;padding:var(--space-xl) 0;">
          <img id="account-avatar" src="" style="width:64px;height:64px;border-radius:50%;object-fit:cover;background:var(--bg-card);margin-bottom:var(--space-md);">
          <div style="font-size:var(--font-size-xl);font-weight:700;" id="account-nickname"></div>
        </div>
        <div class="vip-card" style="margin:0 0 var(--space-lg) 0;">
          <div class="vip-logo">芒果VIP会员</div>
          <div class="vip-benefits" id="account-vip-info"></div>
        </div>
        <div class="menu-group">
          <div class="menu-item"><span class="menu-icon">💰</span><span class="menu-text">余额</span><span class="menu-badge" id="account-balance"></span><span class="menu-arrow">›</span></div>
          <div class="menu-item"><span class="menu-icon">🪙</span><span class="menu-text">芒果币</span><span class="menu-badge" id="account-coins"></span><span class="menu-arrow">›</span></div>
        </div>
      </div>`,

    'page-accountChange': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center">账户明细</div>
        <div class="header-right"></div>
      </div>
      <div class="page-content" id="account-change-list"></div>`,

    'page-buyRecord': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center">购买记录</div>
        <div class="header-right"></div>
      </div>
      <div class="page-content" id="buy-record-list"></div>`,

    'page-unlockRecords': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center">解锁记录</div>
        <div class="header-right"></div>
      </div>
      <div class="page-content" id="unlock-records-list"></div>`,

    'page-share': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center">分享</div>
        <div class="header-right"></div>
      </div>
      <div class="page-content" style="padding:var(--space-xl);text-align:center;">
        <div style="font-size:48px;margin-bottom:var(--space-xl);">📤</div>
        <div style="font-size:var(--font-size-lg);font-weight:600;margin-bottom:var(--space-md);">分享给好友</div>
        <div style="color:var(--text-secondary);margin-bottom:var(--space-xl);">邀请好友一起看芒果TV，双方均可获得3天VIP</div>
        <div style="display:flex;gap:var(--space-lg);justify-content:center;">
          <div style="text-align:center;" onclick="window.App.showToast('微信分享开发中')"><div style="width:48px;height:48px;border-radius:50%;background:#07C160;color:#fff;display:flex;align-items:center;justify-content:center;font-size:24px;margin-bottom:var(--space-sm);">💬</div><span style="font-size:var(--font-size-xs);">微信</span></div>
          <div style="text-align:center;" onclick="window.App.showToast('朋友圈分享开发中')"><div style="width:48px;height:48px;border-radius:50%;background:#576B95;color:#fff;display:flex;align-items:center;justify-content:center;font-size:24px;margin-bottom:var(--space-sm);">🔄</div><span style="font-size:var(--font-size-xs);">朋友圈</span></div>
          <div style="text-align:center;" onclick="window.App.showToast('复制链接成功')"><div style="width:48px;height:48px;border-radius:50%;background:#FF6B35;color:#fff;display:flex;align-items:center;justify-content:center;font-size:24px;margin-bottom:var(--space-sm);">🔗</div><span style="font-size:var(--font-size-xs);">复制链接</span></div>
        </div>
      </div>`,

    'page-shareRecord': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center">分享记录</div>
        <div class="header-right"></div>
      </div>
      <div class="page-content" id="share-record-list"></div>`,

    'page-download': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center">离线下载</div>
        <div class="header-right"></div>
      </div>
      <div class="page-content" id="download-list"></div>`,

    'page-myPost': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center">我的帖子</div>
        <div class="header-right"></div>
      </div>
      <div class="page-content" id="my-post-list"></div>`,

    'page-myTeaPost': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center">我的茶帖</div>
        <div class="header-right"></div>
      </div>
      <div class="page-content" id="my-tea-post-list"></div>`,

    'page-myFavoritesNovels': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center">收藏的小说</div>
        <div class="header-right"></div>
      </div>
      <div class="page-content">
        <div class="video-grid" id="my-fav-novels-grid"></div>
      </div>`,

    'page-myAudioFavorites': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center">收藏的音频</div>
        <div class="header-right"></div>
      </div>
      <div class="page-content">
        <div class="video-grid" id="my-fav-audio-grid"></div>
      </div>`,

    'page-myCommicCollection': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center">收藏的动漫</div>
        <div class="header-right"></div>
      </div>
      <div class="page-content">
        <div class="video-grid" id="my-fav-comic-grid"></div>
      </div>`,

    'page-homeCollect': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center">我的收藏</div>
        <div class="header-right"></div>
      </div>
      <div class="tabs" id="home-collect-tabs">
        <span class="tab active">全部</span>
        <span class="tab">追剧中</span>
        <span class="tab">已看完</span>
      </div>
      <div class="page-content">
        <div class="video-grid" id="home-collect-grid"></div>
      </div>`,

    'page-homeHistory': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center">观看历史</div>
        <div class="header-right"></div>
      </div>
      <div class="page-content">
        <div id="home-history-list"></div>
      </div>`,

    'page-changeInfo': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center">修改资料</div>
        <div class="header-right"><span class="header-icon" id="save-info-btn" style="color:var(--color-primary);">保存</span></div>
      </div>
      <div class="page-content" style="padding:var(--space-lg);">
        <div style="text-align:center;padding:var(--space-lg) 0;">
          <img id="changeinfo-avatar" src="" style="width:72px;height:72px;border-radius:50%;object-fit:cover;background:var(--bg-card);">
          <div style="color:var(--text-secondary);font-size:var(--font-size-sm);margin-top:var(--space-sm);">点击更换头像</div>
        </div>
        <div class="menu-group">
          <div class="menu-item"><span class="menu-text">昵称</span><input type="text" id="changeinfo-nickname" style="flex:1;text-align:right;border:none;background:transparent;color:var(--text-secondary);font-size:var(--font-size-md);"></div>
          <div class="menu-item"><span class="menu-text">性别</span><span class="menu-badge" id="changeinfo-gender" style="cursor:pointer;">男</span><span class="menu-arrow">›</span></div>
          <div class="menu-item"><span class="menu-text">生日</span><span class="menu-badge" id="changeinfo-birthday" style="cursor:pointer;">1995-06-15</span><span class="menu-arrow">›</span></div>
          <div class="menu-item"><span class="menu-text">地区</span><span class="menu-badge" id="changeinfo-region" style="cursor:pointer;">北京</span><span class="menu-arrow">›</span></div>
          <div class="menu-item"><span class="menu-text">签名</span><input type="text" id="changeinfo-signature" placeholder="写一句话介绍自己" style="flex:1;text-align:right;border:none;background:transparent;color:var(--text-secondary);font-size:var(--font-size-md);"></div>
        </div>
      </div>`,

    'page-changePassword': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center">修改密码</div>
        <div class="header-right"></div>
      </div>
      <div class="page-content" style="padding:var(--space-lg);">
        <div style="margin-bottom:var(--space-md);">
          <input type="password" id="change-pw-old" placeholder="请输入旧密码" style="width:100%;padding:var(--space-md);border:1px solid var(--border-color);border-radius:var(--radius-md);font-size:var(--font-size-md);background:var(--bg-card);color:var(--text-primary);box-sizing:border-box;">
        </div>
        <div style="margin-bottom:var(--space-md);">
          <input type="password" id="change-pw-new" placeholder="请输入新密码" style="width:100%;padding:var(--space-md);border:1px solid var(--border-color);border-radius:var(--radius-md);font-size:var(--font-size-md);background:var(--bg-card);color:var(--text-primary);box-sizing:border-box;">
        </div>
        <div style="margin-bottom:var(--space-lg);">
          <input type="password" id="change-pw-confirm" placeholder="请确认新密码" style="width:100%;padding:var(--space-md);border:1px solid var(--border-color);border-radius:var(--radius-md);font-size:var(--font-size-md);background:var(--bg-card);color:var(--text-primary);box-sizing:border-box;">
        </div>
        <div class="login-btn" id="submit-password-btn" style="text-align:center;">确认修改</div>
      </div>`,

    'page-problem': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center">帮助中心</div>
        <div class="header-right"></div>
      </div>
      <div class="page-content">
        <div class="menu-group" style="margin-top:var(--space-lg);" id="problem-list"></div>
      </div>`,

    'page-theme': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center">主题设置</div>
        <div class="header-right"></div>
      </div>
      <div class="page-content" style="padding:var(--space-lg);" id="theme-options"></div>`,

    'page-tea': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center">茶社</div>
        <div class="header-right"><span class="header-icon" data-navigate="addPost">✏️</span></div>
      </div>
      <div class="tabs" id="tea-tabs">
        <span class="tab active">热门</span>
        <span class="tab">最新</span>
        <span class="tab">关注</span>
      </div>
      <div class="page-content" id="tea-post-list"></div>`,

    'page-weimi': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center">微密圈</div>
        <div class="header-right"></div>
      </div>
      <div class="page-content">
        <div class="video-grid" id="weimi-grid"></div>
      </div>`,

    'page-spare': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center">备用页面</div>
        <div class="header-right"></div>
      </div>
      <div class="page-content" style="display:flex;align-items:center;justify-content:center;min-height:60vh;color:var(--text-secondary);font-size:var(--font-size-lg);">
        备用页面 - 建设中
      </div>`,

    'page-shortDramaPlayer': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center" id="shortdrama-player-title">短剧播放</div>
        <div class="header-right"></div>
      </div>
      <div class="player-container" style="padding-top:56.25%;position:relative;background:#000;">
        <div style="position:absolute;top:50%;left:50%;transform:translate(-50%,-50%);font-size:48px;color:#fff;">▶</div>
      </div>
      <div class="episode-section" style="padding:var(--space-lg);">
        <div class="episode-title">剧集列表</div>
        <div class="episode-grid" id="shortdrama-episode-grid"></div>
      </div>`,

    'page-shortPlay': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center">短视频</div>
        <div class="header-right"></div>
      </div>
      <div class="player-container" style="padding-top:56.25%;position:relative;background:#000;">
        <div style="position:absolute;top:50%;left:50%;transform:translate(-50%,-50%);font-size:48px;color:#fff;">▶</div>
      </div>
      <div class="page-content" style="padding:var(--space-lg);">
        <div id="shortplay-info"></div>
      </div>`,

    'page-shortVideoPlaylet': `
      <div class="header" style="background:transparent;">
        <div class="header-left"><span class="header-icon" data-back style="color:#fff;">←</span></div>
        <div class="header-center" id="shortvideo-playlet-title" style="color:#fff;">短剧播放</div>
        <div class="header-right"></div>
      </div>
      <div style="background:#000;display:flex;align-items:center;justify-content:center;height:calc(100vh - var(--header-height));color:#fff;font-size:48px;">▶</div>
      <div class="episode-section" style="padding:var(--space-lg);">
        <div class="episode-title">选集</div>
        <div class="episode-grid" id="shortvideo-playlet-grid"></div>
      </div>`,

    'page-searchResult': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center">搜索结果</div>
        <div class="header-right"></div>
      </div>
      <div class="page-content">
        <div id="search-result-list"></div>
      </div>`,

    'page-pages': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center">全部页面</div>
        <div class="header-right"></div>
      </div>
      <div class="page-content">
        <div class="video-grid" id="pages-grid"></div>
      </div>`,

    'page-actor': `
      <div class="header">
        <div class="header-left"><span class="header-icon" data-back>←</span></div>
        <div class="header-center">明星</div>
        <div class="header-right"><span class="header-icon" data-navigate="search">🔍</span></div>
      </div>
      <div class="page-content">
        <div class="video-grid" id="actor-grid"></div>
      </div>`
  };

  // ==================== TAB BAR 模板 ====================
  const TAB_BAR_HTML = `
    <div class="tabbar" id="tab-bar">
      <div class="tab-item" data-tab="home">
        <span class="tab-icon">🏠</span><span class="tab-label">首页</span>
      </div>
      <div class="tab-item" data-tab="short-drama">
        <span class="tab-icon">🎭</span><span class="tab-label">短剧</span>
      </div>
      <div class="tab-item tab-center" data-tab="publish">
        <div class="tab-icon-wrap"><span class="tab-icon">+</span></div>
        <span class="tab-label">发布</span>
      </div>
      <div class="tab-item" data-tab="anime">
        <span class="tab-icon">🐉</span><span class="tab-label">动漫</span>
      </div>
      <div class="tab-item" data-tab="novel">
        <span class="tab-icon">📖</span><span class="tab-label">小说</span>
      </div>
      <div class="tab-item" data-tab="user">
        <span class="tab-icon">👤</span><span class="tab-label">我的</span>
      </div>
    </div>`;

  // 需要显示 tab bar 的页面
  const TAB_BAR_PAGES = new Set(['page-home', 'page-short-drama', 'page-anime', 'page-novel', 'page-user']);

  // ==================== 路由映射 ====================
  const ROUTE_MAP = {
    'home': 'page-home',
    '': 'page-home',
    'short-drama': 'page-short-drama',
    'anime': 'page-anime',
    'novel': 'page-novel',
    'user': 'page-user',
    'search': 'page-search',
    'bbs': 'page-bbs',
    'collections': 'page-collections',
    'messages': 'page-messages',
    'settings': 'page-settings',
    'vip': 'page-vip',
    'login': 'page-login',
    'audio': 'page-audio',
    'audio-detail': 'page-audio-detail',
    'audioRank': 'page-audio-rank',
    'allDramas': 'page-allDramas',
    'longVideo': 'page-longVideo',
    'shortVideo': 'page-shortVideo',
    'shortList': 'page-shortList',
    'hotVideo': 'page-hotVideo',
    'elites': 'page-elites',
    'disclose': 'page-disclose',
    'overseasNews': 'page-overseasNews',
    'bbs-detail': 'page-bbs-detail',
    'addPost': 'page-addPost',
    'comic-category': 'page-comic-category',
    'comic-detail': 'page-comic-detail',
    'comicRank': 'page-comic-rank',
    'novel-category': 'page-novel-category',
    'novel-detail': 'page-novel-detail',
    'novelRank': 'page-novel-rank',
    'collection-detail': 'page-collection-detail',
    'collection-videos': 'page-collection-videos',
    'account': 'page-account',
    'accountChange': 'page-accountChange',
    'buyRecord': 'page-buyRecord',
    'unlockRecords': 'page-unlockRecords',
    'share': 'page-share',
    'shareRecord': 'page-shareRecord',
    'download': 'page-download',
    'myPost': 'page-myPost',
    'myTeaPost': 'page-myTeaPost',
    'myFavoritesNovels': 'page-myFavoritesNovels',
    'myAudioFavorites': 'page-myAudioFavorites',
    'myCommicCollection': 'page-myCommicCollection',
    'home-collect': 'page-homeCollect',
    'home-history': 'page-homeHistory',
    'changeInfo': 'page-changeInfo',
    'changePassword': 'page-changePassword',
    'problem': 'page-problem',
    'theme': 'page-theme',
    'tea': 'page-tea',
    'spare': 'page-spare',
    'shortDramaPlayer': 'page-shortDramaPlayer',
    'shortPlay': 'page-shortPlay',
    'shortVideoPlaylet': 'page-shortVideoPlaylet',
    'searchResult': 'page-searchResult',
    'pages': 'page-pages',
    'actor': 'page-actor',
    'chapters': 'page-chapters',
    'read': 'page-read'
  };

  const DYNAMIC_ROUTES = {
    'video-play': 'page-video-play',
    'video-list': 'page-video-list',
    'actor': 'page-actor-detail',
    'novel-reader': 'page-novel-reader',
    'audio-detail': 'page-audio-detail',
    'comic-detail': 'page-comic-detail',
    'novel-detail': 'page-novel-detail',
    'bbs-detail': 'page-bbs-detail',
    'collection-detail': 'page-collection-detail',
    'collection-videos': 'page-collection-videos',
    'shortVideoPlaylet': 'page-shortVideoPlaylet',
    'weimi': 'page-weimi'
  };

  // ==================== 状态 ====================
  let currentPageId = null;
  let currentParams = {};
  const navHistory = [];
  let swiperTimer = null;
  let swiperIndex = 0;
  let searchHistoryLocal = [];

  try { searchHistoryLocal = JSON.parse(localStorage.getItem('mg_search_history') || '[]'); } catch (e) {}

  // ==================== DOM 引用 ====================
  const appEl = document.querySelector('.app');
  const pagesContainer = document.querySelector('.pages-container');
  const toastEl = document.getElementById('toast');
  const loadingEl = document.getElementById('start-loading');
  let tabBarEl = null;

  // ==================== 工具函数 ====================

  function $(sel, parent) { return (parent || document).querySelector(sel); }
  function $$(sel, parent) { return (parent || document).querySelectorAll(sel); }

  function escapeHtml(str) {
    const div = document.createElement('div');
    div.textContent = str;
    return div.innerHTML;
  }

  // ==================== Toast ====================
  let toastTimer = null;
  function showToast(message, type) {
    if (toastTimer) { clearTimeout(toastTimer); toastEl.className = 'toast'; }
    toastEl.textContent = message;
    const cls = type === 'loading' ? 'active loading' : 'active';
    if (type === 'top') { toastEl.className = 'toast toast-top ' + cls; }
    else { toastEl.className = 'toast ' + cls; }
    if (type !== 'loading') {
      toastTimer = setTimeout(() => { toastEl.className = 'toast'; }, 2000);
    }
  }

  function hideToast() {
    if (toastTimer) clearTimeout(toastTimer);
    toastEl.className = 'toast';
  }

  // ==================== 渲染辅助函数 ====================

  function renderVideoCard(item) {
    const cover = item.cover || item.thumbnail || '';
    const title = item.title || '';
    const score = item.score || item.rating || '';
    const tags = item.tags || item.genreTags || [];
    const desc = item.description || '';
    const episodeCount = item.episodeCount || '';
    const status = item.status || '';
    const category = item.category || '';

    const tagHtml = tags.slice(0, 2).map(t => `<span class="card-tag">${escapeHtml(t)}</span>`).join('');

    let badgeHtml = '';
    if (status === '连载') badgeHtml = '<span class="card-badge hot">连载</span>';
    else if (status === '完结') badgeHtml = '<span class="card-badge new">完结</span>';
    else if (item.hot) badgeHtml = '<span class="card-badge hot">热播</span>';

    const episodeHtml = episodeCount ? `<span class="card-duration">${episodeCount}集</span>` : '';
    const metaHtml = score ? `<div class="card-meta"><span class="card-score">${score}分</span><span>${escapeHtml(category)}</span></div>` : '';

    return `
      <div class="video-card" data-navigate="video-play/${item.id || 1}">
        <div class="card-poster">
          <img src="${escapeHtml(cover)}" alt="${escapeHtml(title)}" loading="lazy">
          ${badgeHtml}${episodeHtml}
          <div class="card-play"></div>
        </div>
        <div class="card-info">
          <div class="card-title">${escapeHtml(title)}</div>
          <div class="card-tags">${tagHtml}</div>
          ${metaHtml}
        </div>
      </div>`;
  }

  function renderHorizontalCard(item) {
    const cover = item.cover || item.thumbnail || item.poster || '';
    const title = item.title || '';
    const score = item.score || item.rating || '';
    const desc = item.description || item.director || '';
    const episodeCount = item.episodeCount || '';

    return `
      <div class="hcard" data-navigate="video-play/${item.id || 1}">
        <div class="hcard-poster">
          <img src="${escapeHtml(cover)}" alt="${escapeHtml(title)}" loading="lazy">
          ${score ? `<span class="hcard-score">${score}</span>` : ''}
          ${episodeCount ? `<span class="hcard-episode">${episodeCount}集</span>` : ''}
        </div>
        <div class="hcard-title">${escapeHtml(title)}</div>
        ${desc ? `<div class="hcard-desc">${escapeHtml(desc)}</div>` : ''}
      </div>`;
  }

  function renderRankItem(item, rank) {
    const cover = item.thumbnail || item.cover || '';
    const title = item.title || '';
    const tags = item.tags || [];
    const views = item.views || '';
    const ep = item.episodeCount || '';

    return `
      <div class="rank-item" data-navigate="video-play/${item.id || 1}">
        <span class="rank-index">${rank}</span>
        <div class="rank-poster"><img src="${escapeHtml(cover)}" alt="${escapeHtml(title)}" loading="lazy"></div>
        <div class="rank-info">
          <div class="rank-title">${escapeHtml(title)}</div>
          <div class="rank-meta">${tags.slice(0,2).map(t => escapeHtml(t)).join(' · ')} · ${ep}集 · ${escapeHtml(views)}次播放</div>
        </div>
        ${rank <= 3 ? `<span class="rank-trend up">🔥</span>` : ''}
      </div>`;
  }

  function renderPostItem(post) {
    const imgs = (post.images || []).map(url => `<img src="${escapeHtml(url)}" style="width:100%;border-radius:var(--radius-sm);margin-top:var(--space-sm);" loading="lazy">`).join('');

    return `
      <div class="comment-item" data-navigate="bbs">
        <div class="comment-avatar"><img src="${escapeHtml(post.avatar)}" alt="" loading="lazy"></div>
        <div class="comment-body">
          <div class="comment-user">${escapeHtml(post.author)}</div>
          <div class="comment-text" style="font-weight:600;">${escapeHtml(post.title)}</div>
          <div class="comment-text">${escapeHtml(post.content)}</div>
          ${imgs}
          <div class="comment-footer">
            <span class="comment-time">${escapeHtml(post.time)}</span>
            <div class="comment-actions">
              <span class="comment-action">👍 ${post.likes}</span>
              <span class="comment-action">💬 ${post.comments}</span>
            </div>
          </div>
        </div>
      </div>`;
  }

  function renderNovelItem(novel) {
    const cover = novel.cover || '';
    const title = novel.title || '';
    const author = novel.author || '';
    const status = novel.status || '';
    const wordCount = novel.wordCount || '';
    const category = novel.category || '';

    return `
      <div class="video-card" data-navigate="novel-reader/${novel.id || 1}">
        <div class="card-poster">
          <img src="${escapeHtml(cover)}" alt="${escapeHtml(title)}" loading="lazy">
          ${status === '连载' ? '<span class="card-badge hot">连载</span>' : '<span class="card-badge new">完结</span>'}
        </div>
        <div class="card-info">
          <div class="card-title">${escapeHtml(title)}</div>
          <div class="card-tags">
            <span class="card-tag">${escapeHtml(author)}</span>
            <span class="card-tag">${escapeHtml(category)}</span>
          </div>
          <div class="card-meta"><span>${escapeHtml(wordCount)}字</span><span>${escapeHtml(status)}</span></div>
        </div>
      </div>`;
  }

  // ==================== Swiper ====================

  function initSwiper() {
    const bannerWrapper = $('.banner-wrapper');
    const bannerDots = $('.banner-dots');
    if (!bannerWrapper || !bannerDots) return;

    const banners = M.banners;
    if (!banners || !banners.length) return;

    bannerWrapper.innerHTML = banners.map((b, i) => `
      <div class="banner-slide" data-navigate="video-play/${b.linkId.replace('video_', '') || 1}">
        <img src="${escapeHtml(b.imageUrl)}" alt="${escapeHtml(b.title)}" loading="${i === 0 ? 'eager' : 'lazy'}">
        <span class="banner-tag">热播</span>
        <div class="banner-title">${escapeHtml(b.title)}</div>
      </div>
    `).join('');

    bannerDots.innerHTML = banners.map((_, i) => `
      <span class="banner-dot${i === 0 ? ' active' : ''}" data-dot="${i}"></span>
    `).join('');

    swiperIndex = 0;
    updateSwiper();

    if (swiperTimer) clearInterval(swiperTimer);
    swiperTimer = setInterval(() => {
      swiperIndex = (swiperIndex + 1) % banners.length;
      updateSwiper();
    }, 3000);

    // Touch events
    let touchStartX = 0;
    const bannerEl = bannerWrapper.parentElement;
    bannerEl.addEventListener('touchstart', (e) => {
      touchStartX = e.touches[0].clientX;
    }, { passive: true });
    bannerEl.addEventListener('touchend', (e) => {
      const diff = touchStartX - e.changedTouches[0].clientX;
      if (Math.abs(diff) > 40) {
        if (diff > 0) swiperIndex = (swiperIndex + 1) % banners.length;
        else swiperIndex = (swiperIndex - 1 + banners.length) % banners.length;
        updateSwiper();
        resetSwiperTimer();
      }
    });

    // Dot clicks
    bannerDots.addEventListener('click', (e) => {
      const dot = e.target.closest('.banner-dot');
      if (!dot) return;
      swiperIndex = parseInt(dot.dataset.dot);
      updateSwiper();
      resetSwiperTimer();
    });
  }

  function updateSwiper() {
    const wrapper = $('.banner-wrapper');
    const dots = $$('.banner-dot');
    if (!wrapper) return;
    const slideWidth = wrapper.parentElement.clientWidth;
    wrapper.style.transform = `translateX(-${swiperIndex * (slideWidth - 16)}px)`;
    dots.forEach((d, i) => { d.classList.toggle('active', i === swiperIndex); });
  }

  function resetSwiperTimer() {
    if (swiperTimer) clearInterval(swiperTimer);
    swiperTimer = setInterval(() => {
      const banners = M.banners;
      if (!banners || !banners.length) return;
      swiperIndex = (swiperIndex + 1) % banners.length;
      updateSwiper();
    }, 3000);
  }

  // ==================== Router ====================

  function parseHash() {
    const hash = location.hash.replace(/^#\/?/, '');
    if (!hash) return { page: 'home', params: {} };

    const parts = hash.split('/');
    const base = parts[0];

    // 精确匹配
    if (ROUTE_MAP[base]) return { page: base, params: {} };

    // 动态路由匹配
    if (DYNAMIC_ROUTES[base] && parts.length > 1) {
      return { page: base, params: { id: parts.slice(1).join('/') } };
    }
    if (base === 'video-list' && parts.length > 1) {
      return { page: 'video-list', params: { category: parts[1] } };
    }
    if (base === 'chapters' && parts.length > 1) {
      return { page: 'chapters', params: { id: parts[1] } };
    }
    if (base === 'read' && parts.length > 1) {
      return { page: 'read', params: { id: parts.slice(1).join('/') } };
    }

    return { page: 'home', params: {} };
  }

  function navigateTo(hash, params) {
    if (typeof hash === 'object' && hash.hash) { hash = hash.hash; params = hash.params; }
    if (!hash && params && params.hash) { hash = params.hash; }
    const fullHash = params && params.id ? `${hash}/${params.id}` : (params && params.category ? `${hash}/${params.category}` : hash);
    if (location.hash === '#' + fullHash) { handleRoute(fullHash, params); return; }
    navHistory.push({ hash: location.hash.replace('#', ''), params: { ...currentParams } });
    location.hash = fullHash;
  }

  function goBack() {
    if (navHistory.length > 0) {
      const prev = navHistory.pop();
      location.hash = prev.hash || '';
    } else {
      location.hash = 'home';
    }
  }

  function handleRoute(hash, params) {
    const parsed = parseHash();
    const routeKey = parsed.page;
    const routeParams = params || parsed.params;
    currentParams = routeParams;

    let pageId;
    if (ROUTE_MAP[routeKey]) {
      pageId = ROUTE_MAP[routeKey];
    } else if (DYNAMIC_ROUTES[routeKey]) {
      pageId = DYNAMIC_ROUTES[routeKey];
    } else {
      pageId = 'page-home';
    }

    switchPage(pageId, routeParams);
  }

  function switchPage(pageId, params) {
    if (currentPageId === pageId) {
      populatePage(pageId, params);
      return;
    }

    // 隐藏所有页面
    $$('.page').forEach(p => p.classList.remove('active'));

    // 显示目标页面
    let pageEl = document.getElementById(pageId);
    if (!pageEl) {
      pageEl = document.createElement('section');
      pageEl.className = 'page';
      pageEl.id = pageId;
      pageEl.innerHTML = PAGE_TEMPLATES[pageId] || '';
      pagesContainer.appendChild(pageEl);
    }

    pageEl.classList.add('active');
    currentPageId = pageId;

    // Tab bar 显示/隐藏
    updateTabBar(pageId);

    // Tab 高亮
    if (TAB_BAR_PAGES.has(pageId)) {
      const tabName = pageId.replace('page-', '');
      $$('.tab-item[data-tab]').forEach(t => {
        t.classList.toggle('active', t.dataset.tab === tabName);
      });
    } else {
      $$('.tab-item[data-tab]').forEach(t => t.classList.remove('active'));
    }

    // 填充数据
    populatePage(pageId, params);

    // 滚动到顶部
    document.documentElement.scrollTop = 0;
    document.body.scrollTop = 0;
    const scrollContainer = $('.page.active');
    if (scrollContainer) scrollContainer.scrollTop = 0;
  }

  function updateTabBar(pageId) {
    if (!tabBarEl) return;
    tabBarEl.style.display = TAB_BAR_PAGES.has(pageId) ? 'flex' : 'none';
    if (!TAB_BAR_PAGES.has(pageId)) {
      const activePage = $('.page.active');
      if (activePage) activePage.style.paddingBottom = '0';
    } else {
      const activePage = $('.page.active');
      if (activePage) activePage.style.paddingBottom = '';
    }
  }

  // ==================== 页面数据填充 ====================

  function populatePage(pageId, params) {
    switch (pageId) {
      case 'page-home': populateHome(); break;
      case 'page-short-drama': populateShortDrama(); break;
      case 'page-anime': populateAnime(); break;
      case 'page-novel': populateNovel(); break;
      case 'page-user': populateUser(); break;
      case 'page-video-play': populateVideoPlay(params); break;
      case 'page-search': populateSearch(); break;
      case 'page-video-list': populateVideoList(params); break;
      case 'page-actor-detail': populateActorDetail(params); break;
      case 'page-bbs': populateBBS(); break;
      case 'page-collections': populateCollections(); break;
      case 'page-messages': populateMessages(); break;
      case 'page-novel-reader': populateNovelReader(params); break;
      case 'page-login': setupLoginPage(); break;
      case 'page-audio': populateAudio(); break;
      case 'page-audio-detail': populateAudioDetail(params); break;
      case 'page-audio-rank': populateAudioRank(); break;
      case 'page-allDramas': populateAllDramas(); break;
      case 'page-longVideo': populateLongVideo(); break;
      case 'page-shortVideo': populateShortVideo(); break;
      case 'page-shortList': populateShortList(); break;
      case 'page-hotVideo': populateHotVideo(); break;
      case 'page-elites': populateElites(); break;
      case 'page-disclose': populateDisclose(); break;
      case 'page-overseasNews': populateOverseasNews(); break;
      case 'page-bbs-detail': populateBBSDetail(params); break;
      case 'page-addPost': setupAddPost(); break;
      case 'page-comic-category': populateComicCategory(); break;
      case 'page-comic-detail': populateComicDetail(params); break;
      case 'page-comic-rank': populateComicRank(); break;
      case 'page-novel-category': populateNovelCategory(); break;
      case 'page-novel-detail': populateNovelDetail(params); break;
      case 'page-novel-rank': populateNovelRank(); break;
      case 'page-chapters': populateChapters(params); break;
      case 'page-read': populateRead(params); break;
      case 'page-collection-detail': populateCollectionDetail(params); break;
      case 'page-collection-videos': populateCollectionVideos(params); break;
      case 'page-account': populateAccount(); break;
      case 'page-accountChange': populateAccountChange(); break;
      case 'page-buyRecord': populateBuyRecord(); break;
      case 'page-unlockRecords': populateUnlockRecords(); break;
      case 'page-share': populateShare(); break;
      case 'page-shareRecord': populateShareRecord(); break;
      case 'page-download': populateDownload(); break;
      case 'page-myPost': populateMyPost(); break;
      case 'page-myTeaPost': populateMyTeaPost(); break;
      case 'page-myFavoritesNovels': populateMyFavoritesNovels(); break;
      case 'page-myAudioFavorites': populateMyAudioFavorites(); break;
      case 'page-myCommicCollection': populateMyCommicCollection(); break;
      case 'page-homeCollect': populateHomeCollect(); break;
      case 'page-homeHistory': populateHomeHistory(); break;
      case 'page-changeInfo': populateChangeInfo(); break;
      case 'page-changePassword': setupChangePassword(); break;
      case 'page-problem': populateProblem(); break;
      case 'page-theme': populateTheme(); break;
      case 'page-tea': populateTea(); break;
      case 'page-weimi': populateWeimi(params); break;
      case 'page-spare': populateSpare(); break;
      case 'page-shortDramaPlayer': populateShortDramaPlayer(params); break;
      case 'page-shortPlay': populateShortPlay(params); break;
      case 'page-shortVideoPlaylet': populateShortVideoPlaylet(params); break;
      case 'page-searchResult': populateSearchResult(); break;
      case 'page-pages': populatePages(); break;
      case 'page-actor': populateActorList(); break;
    }
  }

  function populateHome() {
    initSwiper();

    const catNavMap = {
      '/recommend': 'video-list/recommend',
      '/short-drama': 'short-drama',
      '/anime': 'anime',
      '/movie': 'video-list/movie',
      '/tv': 'video-list/tv',
      '/variety': 'video-list/variety',
      '/sports': 'video-list/sports',
      '/novel': 'novel'
    };

    const catGrid = $('.category-grid');
    if (catGrid && M.categories) {
      catGrid.innerHTML = M.categories.map(c => `
        <div class="category-item" data-navigate="${catNavMap[c.link] || 'home'}">
          <div class="category-icon"><span>${c.icon}</span></div>
          <span class="category-name">${escapeHtml(c.name)}</span>
        </div>
      `).join('');
    }

    const rankList = $('.rank-list');
    if (rankList && M.hotVideos) {
      rankList.innerHTML = M.hotVideos.map((v, i) => renderRankItem(v, i + 1)).join('');
    }

    const recGrid = $('#home-recommend-grid');
    if (recGrid && M.recommendedVideos) {
      recGrid.innerHTML = M.recommendedVideos.slice(0, 8).map(v => renderVideoCard(v)).join('');
    }

    const sdScroll = $('#home-shortdrama-scroll');
    if (sdScroll && M.shortDramas) {
      sdScroll.innerHTML = M.shortDramas.slice(0, 6).map(v => renderHorizontalCard(v)).join('');
    }

    const varietyScroll = $('#home-variety-scroll');
    if (varietyScroll && M.recommendedVideos) {
      const varietyVids = M.recommendedVideos.filter(v => v.category === '综艺').slice(0, 6);
      varietyScroll.innerHTML = varietyVids.map(v => renderHorizontalCard(v)).join('');
    }

    const movieScroll = $('#home-movie-scroll');
    if (movieScroll && M.longVideos) {
      movieScroll.innerHTML = M.longVideos.slice(0, 6).map(v => renderHorizontalCard(v)).join('');
    }
  }

  function populateShortDrama() {
    const grid = $('#short-drama-grid');
    if (grid && M.shortDramas) {
      grid.innerHTML = M.shortDramas.map(v => renderVideoCard(v)).join('');
    }
  }

  function populateAnime() {
    const grid = $('#anime-grid');
    if (grid && M.comics) {
      grid.innerHTML = M.comics.map(v => renderVideoCard(v)).join('');
    }
  }

  function populateNovel() {
    const grid = $('#novel-grid');
    if (grid && M.novels) {
      grid.innerHTML = M.novels.map(v => renderNovelItem(v)).join('');
    }
  }

  function populateUser() {
    const user = M.user;
    if (!user) return;
    const av = $('#user-avatar-img'); if (av) av.src = user.avatar;
    const nn = $('#user-nickname'); if (nn) nn.textContent = user.nickname;
    const vb = $('#user-vip-badge'); if (vb) vb.textContent = 'VIP' + user.vipLevel;
    const ud = $('#user-desc'); if (ud) ud.textContent = 'VIP到期：' + user.vipExpireDate;
    const uf = $('#user-favorites'); if (uf) uf.textContent = user.favoritesCount;
    const uh = $('#user-history'); if (uh) uh.textContent = user.historyCount;
    const ufw = $('#user-following'); if (ufw) ufw.textContent = user.followingCount;
    const ufr = $('#user-followers'); if (ufr) ufr.textContent = user.followersCount;
    const mfc = $('#menu-favorites-count'); if (mfc) mfc.textContent = user.favoritesCount + '个';
  }

  function populateVideoPlay(params) {
    const detail = M.videoDetail;
    if (!detail) return;

    const vt = $('#video-detail-title'); if (vt) vt.textContent = detail.title;
    const vs = $('#video-detail-score'); if (vs) vs.textContent = detail.score + '分';
    const vy = $('#video-detail-year'); if (vy) vy.textContent = detail.year + ' · ' + detail.region;
    const vvw = $('#video-detail-views'); if (vvw) vvw.textContent = detail.views + '次播放';
    const vd = $('#video-detail-desc'); if (vd) vd.textContent = detail.description;
    const vtag = $('#video-detail-tags');
    if (vtag) vtag.innerHTML = (detail.tags || []).map(t => `<span class="video-tag-item">${escapeHtml(t)}</span>`).join('');
    const ttime = $('#player-total-time');
    if (ttime && detail.episodes && detail.episodes.length > 0) {
      ttime.textContent = detail.episodes[0].duration || '45:12';
    }

    const epGrid = $('#episode-grid');
    if (epGrid && detail.episodes) {
      epGrid.innerHTML = detail.episodes.map((ep, i) => `
        <span class="episode-item${i === 0 ? ' active' : ''}${!ep.free ? ' style="position:relative;"' : ''}" data-ep="${i}">
          ${ep.title.replace('第', '').replace('集', '').replace('（大结局）', '')}
          ${!ep.free ? '<span style="position:absolute;top:2px;right:4px;font-size:8px;color:#D4A843;">VIP</span>' : ''}
        </span>
      `).join('');
    }

    const relatedScroll = $('#related-video-scroll');
    if (relatedScroll && detail.relatedVideos) {
      relatedScroll.innerHTML = detail.relatedVideos.map(v => renderHorizontalCard(v)).join('');
    }

    if (detail.actors && detail.actors.length > 0) {
      const actorSection = $('#actor-section');
      const actorScroll = $('#actor-scroll');
      if (actorSection) actorSection.style.display = '';
      if (actorScroll) {
        actorScroll.innerHTML = detail.actors.map(a => `
          <div class="hcard" data-navigate="actor/${a.name}" style="width:80px;">
            <div class="hcard-poster" style="height:80px;border-radius:50%;">
              <img src="${escapeHtml(a.avatar)}" alt="${escapeHtml(a.name)}">
            </div>
            <div class="hcard-title" style="text-align:center;font-size:var(--font-size-xs);">${escapeHtml(a.name)}</div>
            <div class="hcard-desc" style="text-align:center;">饰 ${escapeHtml(a.role)}</div>
          </div>
        `).join('');
      }
    }
  }

  function populateSearch() {
    const hotTags = $('#search-hot-tags');
    if (hotTags && M.searchSuggestions) {
      hotTags.innerHTML = M.searchSuggestions.map(s => `
        <span class="search-hot-tag${s.hot ? ' hot' : ''}" data-keyword="${escapeHtml(s.keyword)}">
          ${s.hot ? '🔥 ' : ''}${escapeHtml(s.keyword)}<span style="font-size:10px;color:var(--text-placeholder);margin-left:4px;">${escapeHtml(s.count)}</span>
        </span>
      `).join('');
    }

    updateSearchHistoryUI();
  }

  function populateVideoList(params) {
    const titleEl = $('#video-list-title');
    const grid = $('#video-list-grid');
    if (!grid) return;
    const category = (params && params.category) || '';
    const catNames = { hot: '热播排行', recommend: '推荐视频', variety: '综艺', movie: '电影', tv: '电视剧', anime: '动漫', sports: '体育' };
    if (titleEl) titleEl.textContent = catNames[category] || '视频列表';

    let data = [];
    switch (category) {
      case 'hot': data = M.hotVideos; break;
      case 'movie': data = M.longVideos; break;
      case 'variety': data = (M.recommendedVideos || []).filter(v => v.category === '综艺'); break;
      case 'tv': data = (M.recommendedVideos || []).filter(v => v.category === '电视剧'); break;
      case 'anime': case 'recommend': data = (M.recommendedVideos || []); break;
      case 'sports': data = M.sports || []; break;
      default: data = M.recommendedVideos || []; break;
    }
    grid.innerHTML = data.map(v => renderVideoCard(v)).join('');
  }

  function populateActorDetail(params) {
    const actorId = params && params.id;
    const actorName = decodeURIComponent(actorId || '');
    const actor = (M.actors || []).find(a => a.name === actorName);
    if (!actor) return;

    const an = $('#actor-name'); if (an) an.textContent = actor.name;
    const aa = $('#actor-avatar'); if (aa) aa.src = actor.avatar;
    const adn = $('#actor-detail-name'); if (adn) adn.textContent = actor.name;
    const ad = $('#actor-desc'); if (ad) ad.textContent = actor.description;
    const ag = $('#actor-video-grid');
    if (ag && actor.relatedVideos) {
      ag.innerHTML = actor.relatedVideos.map(v => renderVideoCard(v)).join('');
    }
  }

  function populateBBS() {
    const list = $('#bbs-post-list');
    if (list && M.posts) {
      list.innerHTML = M.posts.map(p => renderPostItem(p)).join('');
    }
  }

  function populateCollections() {
    const grid = $('#collections-grid');
    if (grid && M.collections) {
      grid.innerHTML = M.collections.map(c => `
        <div class="video-card" data-navigate="video-list/collection">
          <div class="card-poster" style="position:relative;">
            <img src="${escapeHtml(c.cover)}" alt="${escapeHtml(c.name)}" loading="lazy">
            <span class="card-badge exclusive">${c.count}部</span>
          </div>
          <div class="card-info">
            <div class="card-title">${escapeHtml(c.name)}</div>
            <div class="card-meta"><span>${c.count}个视频</span></div>
          </div>
        </div>
      `).join('');
    }
  }

  function populateMessages() {
    const list = $('#messages-list');
    if (!list) return;
    const msgs = [
      { icon: '🔔', title: '系统通知', desc: '欢迎来到芒果TV！', time: '刚刚', unread: true },
      { icon: '💬', title: '评论回复', desc: '你的评论获得了128个赞', time: '1小时前', unread: true },
      { icon: '❤️', title: '喜欢', desc: '追剧达人赞了你的帖子', time: '3小时前', unread: false },
      { icon: '⭐', title: '收藏提醒', desc: '《狂飙》更新了第39集', time: '昨天', unread: false },
      { icon: '🎁', title: '活动通知', desc: 'VIP会员限时优惠，立减30元', time: '2天前', unread: false }
    ];
    list.innerHTML = msgs.map(m => `
      <div class="notification-item${m.unread ? ' unread' : ''}">
        <div class="notification-icon">${m.icon}</div>
        <div class="notification-body">
          <div class="notification-title">${escapeHtml(m.title)}</div>
          <div class="notification-desc">${escapeHtml(m.desc)}</div>
          <div class="notification-time">${escapeHtml(m.time)}</div>
        </div>
      </div>
    `).join('');
  }

  function populateNovelReader(params) {
    const novelId = params && params.id ? parseInt(params.id) : 1;
    const novel = (M.novels || []).find(n => n.id === novelId) || M.novels[0];
    if (!novel) return;
    const titleEl = $('#novel-reader-title'); if (titleEl) titleEl.textContent = novel.title;
    const contentEl = $('#novel-reader-content');
    if (!contentEl) return;
    const desc = novel.description || '';
    const chs = (novel.chapters || []).map(c =>
      `<div style="padding:var(--space-md);background:var(--bg-card);border-radius:var(--radius-md);margin-bottom:var(--space-sm);">
        <span style="color:var(--text-primary);">${escapeHtml(c.title)}</span>
        <span style="float:right;font-size:var(--font-size-xs);color:${c.free ? 'var(--color-success)' : '#D4A843'};">${c.free ? '免费' : 'VIP'}</span>
      </div>`
    ).join('');
    contentEl.innerHTML = `<div style="margin-bottom:var(--space-xl);color:var(--text-secondary);line-height:1.8;">${escapeHtml(desc)}</div>
      <div style="font-size:var(--font-size-lg);font-weight:600;margin-bottom:var(--space-md);">目录</div>${chs}`;
  }

  function setupLoginPage() {
    const phoneBtn = $('#login-phone-btn');
    const wechatBtn = $('#login-wechat-btn');
    if (phoneBtn) phoneBtn.addEventListener('click', () => showToast('手机号登录功能开发中', 'info'));
    if (wechatBtn) wechatBtn.addEventListener('click', () => showToast('微信登录功能开发中', 'info'));
  }

  function populateAudio() {
    const grid = $('#audio-grid');
    if (grid && M.audios) {
      grid.innerHTML = M.audios.map(v => renderVideoCard(v)).join('');
    }
  }

  function populateAudioDetail(params) {
    const audioId = params && params.id ? parseInt(params.id) : 1;
    const audio = (M.audios || []).find(a => a.id === audioId) || M.audios[0];
    if (!audio) return;
    const titleEl = $('#audio-detail-title'); if (titleEl) titleEl.textContent = audio.title;
    const coverEl = $('#audio-detail-cover'); if (coverEl) coverEl.src = audio.cover;
    const nameEl = $('#audio-detail-name'); if (nameEl) nameEl.textContent = audio.title;
    const authorEl = $('#audio-detail-author'); if (authorEl) authorEl.textContent = '原著：' + audio.author;
    const metaEl = $('#audio-detail-meta'); if (metaEl) metaEl.textContent = audio.episodeCount + '集 · ' + audio.plays + '次播放';
    const descEl = $('#audio-detail-desc');
    if (descEl) descEl.innerHTML = '<div style="font-weight:600;margin-bottom:var(--space-sm);">内容简介</div><div style="color:var(--text-secondary);line-height:1.8;">来自' + escapeHtml(audio.author) + '的经典作品《' + escapeHtml(audio.title) + '》，共' + audio.episodeCount + '集，' + audio.plays + '次播放。</div>';
    const epList = $('#audio-episode-list');
    if (epList) {
      const eps = [];
      for (let i = 1; i <= Math.min(audio.episodeCount, 20); i++) {
        eps.push('<div class="menu-item"><span class="menu-text">第' + i + '集</span><span class="menu-arrow">▶</span></div>');
      }
      epList.innerHTML = eps.join('');
    }
  }

  function populateAudioRank() {
    const list = $('#audio-rank-list');
    if (list && M.audios) {
      list.innerHTML = M.audios.map((v, i) => renderRankItem(v, i + 1)).join('');
    }
  }

  function populateAllDramas() {
    const grid = $('#all-dramas-grid');
    if (grid && M.longVideos) {
      grid.innerHTML = M.longVideos.map(v => renderVideoCard(v)).join('');
    }
  }

  function populateLongVideo() {
    const grid = $('#long-video-grid');
    if (grid && M.longVideos) {
      grid.innerHTML = M.longVideos.map(v => renderVideoCard(v)).join('');
    }
  }

  function populateShortVideo() {
    const grid = $('#short-video-grid');
    if (grid && M.shortVideos) {
      grid.innerHTML = M.shortVideos.map(v => renderVideoCard(v)).join('');
    }
  }

  function populateShortList() {
    const grid = $('#short-list-grid');
    if (grid && M.shortDramas) {
      grid.innerHTML = M.shortDramas.map(v => renderVideoCard(v)).join('');
    }
  }

  function populateHotVideo() {
    const grid = $('#hot-video-grid');
    if (grid && M.hotVideos) {
      grid.innerHTML = M.hotVideos.map(v => renderVideoCard(v)).join('');
    }
  }

  function populateElites() {
    const grid = $('#elites-grid');
    if (grid && M.recommendedVideos) {
      grid.innerHTML = M.recommendedVideos.slice(0, 12).map(v => renderVideoCard(v)).join('');
    }
  }

  function populateDisclose() {
    const grid = $('#disclose-grid');
    if (grid && M.recommendedVideos) {
      grid.innerHTML = M.recommendedVideos.map(v => renderVideoCard(v)).join('');
    }
  }

  function populateOverseasNews() {
    const grid = $('#overseas-news-grid');
    if (grid && M.sports) {
      grid.innerHTML = M.sports.map(v => renderVideoCard(v)).join('');
    }
  }

  function populateBBSDetail(params) {
    const postId = params && params.id ? parseInt(params.id) : 1;
    const post = (M.posts || []).find(p => p.id === postId) || M.posts[0];
    if (!post) return;
    const postEl = $('#bbs-detail-post');
    if (postEl) {
      postEl.innerHTML = '<div class="comment-item"><div class="comment-avatar"><img src="' + escapeHtml(post.avatar) + '" alt="" loading="lazy"></div><div class="comment-body"><div class="comment-user">' + escapeHtml(post.author) + '</div><div class="comment-text" style="font-weight:600;font-size:var(--font-size-lg);">' + escapeHtml(post.title) + '</div><div class="comment-text" style="margin-top:var(--space-md);">' + escapeHtml(post.content) + '</div><div class="comment-footer"><span class="comment-time">' + escapeHtml(post.time) + '</span><div class="comment-actions"><span class="comment-action">👍 ' + post.likes + '</span><span class="comment-action">💬 ' + post.comments + '</span></div></div></div></div>';
    }
    const repliesEl = $('#bbs-detail-replies');
    if (repliesEl) {
      repliesEl.innerHTML = '<div class="comment-item"><div class="comment-avatar"><img src="https://picsum.photos/100/100?random=301" alt="" loading="lazy"></div><div class="comment-body"><div class="comment-user">热心网友</div><div class="comment-text">写得太好了，深有同感！</div><div class="comment-footer"><span class="comment-time">1小时前</span><div class="comment-actions"><span class="comment-action">👍 128</span><span class="comment-action">💬 回复</span></div></div></div></div>';
    }
  }

  function setupAddPost() {
    const submitBtn = $('#submit-post-btn');
    if (submitBtn) {
      submitBtn.addEventListener('click', () => {
        const title = ($('#add-post-title') || {}).value;
        const content = ($('#add-post-content') || {}).value;
        if (!title || !title.trim()) { showToast('请输入标题'); return; }
        if (!content || !content.trim()) { showToast('请输入内容'); return; }
        showToast('发布成功！');
        setTimeout(() => { goBack(); }, 1000);
      });
    }
  }

  function populateComicCategory() {
    const grid = $('#comic-category-grid');
    if (grid && M.comics) {
      grid.innerHTML = M.comics.map(v => renderVideoCard(v)).join('');
    }
  }

  function populateComicDetail(params) {
    const comicId = params && params.id ? parseInt(params.id) : 1;
    const comic = (M.comics || []).find(c => c.id === comicId) || M.comics[0];
    if (!comic) return;
    const titleEl = $('#comic-detail-title'); if (titleEl) titleEl.textContent = comic.title;
    const coverEl = $('#comic-detail-cover'); if (coverEl) coverEl.src = comic.cover;
    const nameEl = $('#comic-detail-name'); if (nameEl) nameEl.textContent = comic.title;
    const authorEl = $('#comic-detail-author'); if (authorEl) authorEl.textContent = '作者：' + comic.author;
    const statusEl = $('#comic-detail-status'); if (statusEl) statusEl.textContent = '状态：' + comic.status;
    const latestEl = $('#comic-detail-latest'); if (latestEl) latestEl.textContent = '最新：' + (comic.latestChapter || '');
    const descEl = $('#comic-detail-desc'); if (descEl) descEl.textContent = comic.description || comic.author + '的作品《' + comic.title + '》，' + comic.status + '中，分类：' + (comic.category || '');
    const chList = $('#comic-chapter-list');
    if (chList) {
      const count = 30;
      const html = [];
      for (let i = 1; i <= count; i++) {
        html.push('<div class="menu-item" data-navigate="video-play/' + comic.id + '"><span class="menu-text">第' + i + '话</span><span class="menu-badge" style="font-size:var(--font-size-xs);color:' + (i <= 3 ? 'var(--color-success)' : '#D4A843') + ';">' + (i <= 3 ? '免费' : 'VIP') + '</span><span class="menu-arrow">›</span></div>');
      }
      chList.innerHTML = html.join('');
    }
  }

  function populateComicRank() {
    const list = $('#comic-rank-list');
    if (list && M.comics) {
      list.innerHTML = M.comics.map((v, i) => renderRankItem(v, i + 1)).join('');
    }
  }

  function populateNovelCategory() {
    const grid = $('#novel-category-grid');
    if (grid && M.novels) {
      grid.innerHTML = M.novels.map(v => renderNovelItem(v)).join('');
    }
  }

  function populateNovelDetail(params) {
    const novelId = params && params.id ? parseInt(params.id) : 1;
    const novel = (M.novels || []).find(n => n.id === novelId) || M.novels[0];
    if (!novel) return;
    const titleEl = $('#novel-detail-title'); if (titleEl) titleEl.textContent = novel.title;
    const coverEl = $('#novel-detail-cover'); if (coverEl) coverEl.src = novel.cover;
    const nameEl = $('#novel-detail-name'); if (nameEl) nameEl.textContent = novel.title;
    const authorEl = $('#novel-detail-author'); if (authorEl) authorEl.textContent = '作者：' + novel.author;
    const catEl = $('#novel-detail-category'); if (catEl) catEl.textContent = '分类：' + novel.category;
    const wordsEl = $('#novel-detail-words'); if (wordsEl) wordsEl.textContent = '字数：' + novel.wordCount;
    const statusEl = $('#novel-detail-status'); if (statusEl) statusEl.textContent = '状态：' + novel.status;
    const descEl = $('#novel-detail-desc'); if (descEl) descEl.textContent = novel.description;
    const chList = $('#novel-detail-chapter-list');
    if (chList && novel.chapters) {
      chList.innerHTML = novel.chapters.map(c => '<div class="menu-item" data-navigate="read/' + novel.id + '/' + c.id + '"><span class="menu-text">' + escapeHtml(c.title) + '</span><span class="menu-badge" style="font-size:var(--font-size-xs);color:' + (c.free ? 'var(--color-success)' : '#D4A843') + ';">' + (c.free ? '免费' : 'VIP') + '</span><span class="menu-arrow">›</span></div>').join('');
    }
    document.addEventListener('click', function novelDetailHandler(e) {
      const menuItem = e.target.closest('#novel-detail-chapter-list .menu-item');
      if (!menuItem) return;
      const nav = menuItem.dataset.navigate;
      if (nav) navigateTo(nav);
    });
  }

  function populateNovelRank() {
    const list = $('#novel-rank-list');
    if (list && M.novels) {
      list.innerHTML = M.novels.map((v, i) => renderRankItem(v, i + 1)).join('');
    }
  }

  function populateChapters(params) {
    const novelId = params && params.id ? parseInt(params.id) : 1;
    const novel = (M.novels || []).find(n => n.id === novelId) || M.novels[0];
    if (!novel) return;
    const titleEl = $('#chapters-novel-title'); if (titleEl) titleEl.textContent = novel.title + ' 目录';
    const listEl = $('#chapters-list');
    if (!listEl) return;
    const chapters = novel.chapters || [];
    listEl.innerHTML = chapters.map(c => '<div class="menu-item" data-navigate="read/' + novel.id + '/' + c.id + '" style="display:flex;align-items:center;"><span style="margin-right:var(--space-sm);font-size:var(--font-size-sm);">' + (c.free ? '🔓' : '🔒') + '</span><span class="menu-text">' + escapeHtml(c.title) + '</span><span class="menu-badge">' + (c.free ? '免费' : 'VIP') + '</span><span class="menu-arrow">›</span></div>').join('');
  }

  function populateRead(params) {
    const novelId = params && params.id ? parseInt(params.id) : 1;
    const chapterId = params && params.id ? (novelId ? (params.id.toString().split('/')[1] ? parseInt(params.id.toString().split('/')[1]) : 1) : 1) : 1;
    const novel = (M.novels || []).find(n => n.id === novelId) || M.novels[0];
    if (!novel) return;
    const titleEl = $('#read-novel-title'); if (titleEl) titleEl.textContent = novel.title;
    const contentEl = $('#read-content');
    if (!contentEl) return;
    const chTitle = (novel.chapters || []).find(c => c.id === chapterId);
    const chapTitleStr = chTitle ? chTitle.title : '第一章';
    contentEl.innerHTML = '<div style="text-align:center;font-size:var(--font-size-xl);font-weight:700;margin-bottom:var(--space-xl);">' + escapeHtml(chapTitleStr) + '</div><div style="line-height:2.2;color:var(--text-primary);">' + escapeHtml(novel.description) + '</div><div style="line-height:2.2;color:var(--text-primary);margin-top:var(--space-lg);">' + escapeHtml(novel.description).repeat(3) + '</div>';
  }

  function populateCollectionDetail(params) {
    const collId = params && params.id ? parseInt(params.id) : 1;
    const coll = (M.collections || []).find(c => c.id === collId) || M.collections[0];
    if (!coll) return;
    const infoEl = $('#collection-detail-info');
    if (infoEl) {
      infoEl.innerHTML = '<div style="display:flex;gap:var(--space-lg);margin-bottom:var(--space-xl);"><img src="' + escapeHtml(coll.cover) + '" style="width:120px;height:67px;border-radius:var(--radius-md);object-fit:cover;"><div><div style="font-size:var(--font-size-xl);font-weight:700;margin-bottom:var(--space-sm);">' + escapeHtml(coll.name) + '</div><div style="color:var(--text-secondary);font-size:var(--font-size-sm);">共' + coll.count + '个视频</div></div></div>';
    }
    const grid = $('#collection-detail-grid');
    if (grid && coll.videos) {
      grid.innerHTML = coll.videos.map(v => renderVideoCard(v)).join('');
    }
  }

  function populateCollectionVideos(params) {
    const collId = params && params.id ? parseInt(params.id) : 1;
    const coll = (M.collections || []).find(c => c.id === collId) || M.collections[0];
    if (!coll) return;
    const titleEl = $('#collection-videos-title'); if (titleEl) titleEl.textContent = coll.name;
    const grid = $('#collection-videos-grid');
    if (grid && coll.videos) {
      grid.innerHTML = coll.videos.map(v => renderVideoCard(v)).join('');
    }
  }

  function populateAccount() {
    const user = M.user;
    if (!user) return;
    const av = $('#account-avatar'); if (av) av.src = user.avatar;
    const nn = $('#account-nickname'); if (nn) nn.textContent = user.nickname;
    const vi = $('#account-vip-info'); if (vi) vi.textContent = 'VIP' + user.vipLevel + ' · 到期日：' + user.vipExpireDate;
    const bal = $('#account-balance'); if (bal) bal.textContent = '¥' + user.balance;
    const coins = $('#account-coins'); if (coins) coins.textContent = user.coins + '个';
  }

  function populateAccountChange() {
    const list = $('#account-change-list');
    if (!list) return;
    const records = [
      { title: 'VIP会员续费', amount: '-¥15.00', time: '2024-06-15', type: 'expense' },
      { title: '充值芒果币', amount: '+¥30.00', time: '2024-06-10', type: 'income' },
      { title: '购买剧集《狂飙》第4集', amount: '-¥2.00', time: '2024-06-08', type: 'expense' },
      { title: '分享奖励', amount: '+¥5.00', time: '2024-06-05', type: 'income' },
      { title: 'VIP会员续费', amount: '-¥15.00', time: '2024-05-15', type: 'expense' }
    ];
    list.innerHTML = records.map(r => '<div class="menu-item"><span class="menu-icon">' + (r.type === 'income' ? '💰' : '💸') + '</span><span class="menu-text">' + escapeHtml(r.title) + '</span><span class="menu-badge" style="color:' + (r.type === 'income' ? 'var(--color-success)' : '') + ';">' + r.amount + '</span><span class="menu-arrow">' + escapeHtml(r.time) + '</span></div>').join('');
  }

  function populateBuyRecord() {
    const list = $('#buy-record-list');
    if (!list) return;
    const records = [
      { title: '狂飙 第4集', amount: '¥2.00', time: '2024-06-08', orderNo: 'MG20240608001' },
      { title: '繁花 第5集', amount: '¥2.00', time: '2024-06-01', orderNo: 'MG20240601002' },
      { title: '芒果VIP月度会员', amount: '¥15.00', time: '2024-05-15', orderNo: 'MG20240515003' }
    ];
    list.innerHTML = records.map(r => '<div class="menu-item"><span class="menu-icon">📺</span><div><div class="menu-text">' + escapeHtml(r.title) + '</div><div style="font-size:var(--font-size-xs);color:var(--text-tertiary);">订单号：' + r.orderNo + '</div></div><span class="menu-badge">' + r.amount + '</span><span class="menu-arrow">' + escapeHtml(r.time) + '</span></div>').join('');
  }

  function populateUnlockRecords() {
    const list = $('#unlock-records-list');
    if (!list) return;
    const records = [
      { title: '狂飙 第4集', time: '2024-06-08', cost: '50芒果币' },
      { title: '繁花 第5集', time: '2024-06-01', cost: '50芒果币' },
      { title: '斗破苍穹 第103话', time: '2024-05-20', cost: '30芒果币' }
    ];
    list.innerHTML = records.map(r => '<div class="menu-item"><span class="menu-icon">🔓</span><span class="menu-text">' + escapeHtml(r.title) + '</span><span class="menu-badge">' + r.cost + '</span><span class="menu-arrow">' + escapeHtml(r.time) + '</span></div>').join('');
  }

  function populateShare() {}

  function populateShareRecord() {
    const list = $('#share-record-list');
    if (!list) return;
    const records = [
      { name: '张三', time: '2024-06-15', reward: '+3天VIP' },
      { name: '李四', time: '2024-06-10', reward: '+3天VIP' },
      { name: '王五', time: '2024-06-05', reward: '+3天VIP' }
    ];
    list.innerHTML = records.map(r => '<div class="menu-item"><span class="menu-icon">👤</span><span class="menu-text">分享给 ' + escapeHtml(r.name) + '</span><span class="menu-badge" style="color:var(--color-success);">' + r.reward + '</span><span class="menu-arrow">' + escapeHtml(r.time) + '</span></div>').join('');
  }

  function populateDownload() {
    const list = $('#download-list');
    if (!list) return;
    const items = [
      { title: '狂飙 第1-3集', size: '1.2GB', status: '已完成', progress: 100 },
      { title: '繁花 第1集', size: '450MB', status: '下载中', progress: 65 },
      { title: '斗破苍穹 第350话', size: '280MB', status: '已暂停', progress: 42 }
    ];
    list.innerHTML = items.map(d => '<div class="menu-item"><span class="menu-icon">📥</span><div><div class="menu-text">' + escapeHtml(d.title) + '</div><div style="font-size:var(--font-size-xs);color:var(--text-tertiary);">' + d.size + ' · ' + d.status + '</div><div style="height:3px;background:var(--bg-card);border-radius:2px;margin-top:var(--space-sm);"><div style="height:100%;width:' + d.progress + '%;background:var(--color-primary);border-radius:2px;"></div></div></div><span class="menu-arrow">›</span></div>').join('');
  }

  function populateMyPost() {
    const list = $('#my-post-list');
    if (list && M.posts) {
      list.innerHTML = M.posts.slice(0, 5).map(p => renderPostItem(p)).join('');
    }
  }

  function populateMyTeaPost() {
    const list = $('#my-tea-post-list');
    if (list && M.posts) {
      list.innerHTML = M.posts.slice(2, 7).map(p => renderPostItem(p)).join('');
    }
  }

  function populateMyFavoritesNovels() {
    const grid = $('#my-fav-novels-grid');
    if (grid && M.novels) {
      grid.innerHTML = M.novels.slice(0, 6).map(v => renderNovelItem(v)).join('');
    }
  }

  function populateMyAudioFavorites() {
    const grid = $('#my-fav-audio-grid');
    if (grid && M.audios) {
      grid.innerHTML = M.audios.slice(0, 6).map(v => renderVideoCard(v)).join('');
    }
  }

  function populateMyCommicCollection() {
    const grid = $('#my-fav-comic-grid');
    if (grid && M.comics) {
      grid.innerHTML = M.comics.slice(0, 6).map(v => renderVideoCard(v)).join('');
    }
  }

  function populateHomeCollect() {
    const grid = $('#home-collect-grid');
    if (grid && M.recommendedVideos) {
      grid.innerHTML = M.recommendedVideos.slice(0, 12).map(v => {
        const progress = Math.floor(Math.random() * 100);
        return '<div class="video-card" data-navigate="video-play/' + (v.id || 1) + '"><div class="card-poster"><img src="' + escapeHtml(v.cover) + '" alt="' + escapeHtml(v.title) + '" loading="lazy"><div style="position:absolute;bottom:0;left:0;right:0;height:3px;background:rgba(255,255,255,0.3);"><div style="height:100%;width:' + progress + '%;background:var(--color-primary);"></div></div></div><div class="card-info"><div class="card-title">' + escapeHtml(v.title) + '</div><div class="card-meta"><span>已看' + progress + '%</span></div></div></div>';
      }).join('');
    }
  }

  function populateHomeHistory() {
    const list = $('#home-history-list');
    if (list && M.recommendedVideos) {
      list.innerHTML = M.recommendedVideos.slice(0, 10).map(v => {
        const progress = Math.floor(Math.random() * 100);
        return '<div class="menu-item" data-navigate="video-play/' + (v.id || 1) + '"><img src="' + escapeHtml(v.cover) + '" style="width:100px;height:56px;border-radius:var(--radius-sm);object-fit:cover;"><div style="flex:1;margin-left:var(--space-md);"><div class="menu-text">' + escapeHtml(v.title) + '</div><div style="font-size:var(--font-size-xs);color:var(--text-tertiary);">已看' + progress + '%</div><div style="height:2px;background:var(--bg-card);margin-top:var(--space-sm);border-radius:1px;"><div style="height:100%;width:' + progress + '%;background:var(--color-primary);border-radius:1px;"></div></div></div><span class="menu-arrow">›</span></div>';
      }).join('');
    }
  }

  function populateChangeInfo() {
    const user = M.user;
    if (!user) return;
    const av = $('#changeinfo-avatar'); if (av) av.src = user.avatar;
    const nn = $('#changeinfo-nickname'); if (nn) nn.value = user.nickname;
    document.getElementById('changeinfo-avatar').addEventListener('click', () => { showToast('头像更换功能开发中'); });
    const saveBtn = $('#save-info-btn');
    if (saveBtn) {
      saveBtn.addEventListener('click', () => {
        showToast('资料保存成功');
        setTimeout(() => { goBack(); }, 1000);
      });
    }
  }

  function setupChangePassword() {
    const submitBtn = $('#submit-password-btn');
    if (submitBtn) {
      submitBtn.addEventListener('click', () => {
        const oldPw = ($('#change-pw-old') || {}).value;
        const newPw = ($('#change-pw-new') || {}).value;
        const confirmPw = ($('#change-pw-confirm') || {}).value;
        if (!oldPw) { showToast('请输入旧密码'); return; }
        if (!newPw) { showToast('请输入新密码'); return; }
        if (newPw !== confirmPw) { showToast('两次密码不一致'); return; }
        showToast('密码修改成功');
        setTimeout(() => { goBack(); }, 1000);
      });
    }
  }

  function populateProblem() {
    const list = $('#problem-list');
    if (!list) return;
    const faqs = [
      { q: '如何开通VIP会员？', a: '点击"我的"页面的会员卡片即可开通' },
      { q: '如何下载视频离线观看？', a: '在视频详情页点击下载按钮即可' },
      { q: '芒果币如何使用？', a: '用于解锁付费剧集和购买虚拟道具' },
      { q: '如何修改个人信息？', a: '在设置-个人信息中修改' },
      { q: '忘记密码怎么办？', a: '使用手机号验证码登录后可重置密码' },
      { q: '如何联系客服？', a: '客服热线：400-888-8888' }
    ];
    list.innerHTML = faqs.map(f => '<div class="menu-item" onclick="window.App.showToast(\'' + f.a + '\')"><span class="menu-icon">❓</span><span class="menu-text">' + escapeHtml(f.q) + '</span><span class="menu-arrow">›</span></div>').join('');
  }

  function populateTheme() {
    const el = $('#theme-options');
    if (!el) return;
    el.innerHTML = '<div class="menu-group"><div class="menu-item" onclick="document.body.classList.toggle(\'dark-theme\');window.App.showToast(\'主题切换成功\')"><span class="menu-icon">🌙</span><span class="menu-text">深色模式</span><span class="menu-arrow">›</span></div><div class="menu-item" onclick="window.App.showToast(\'浅色主题已应用\')"><span class="menu-icon">☀️</span><span class="menu-text">浅色模式</span><span class="menu-arrow">›</span></div><div class="menu-item" onclick="window.App.showToast(\'跟随系统设置\')"><span class="menu-icon">🔄</span><span class="menu-text">跟随系统</span><span class="menu-arrow">›</span></div></div>';
  }

  function populateTea() {
    const list = $('#tea-post-list');
    if (list && M.posts) {
      list.innerHTML = M.posts.map(p => renderPostItem(p)).join('');
    }
  }

  function populateWeimi(params) {
    const grid = $('#weimi-grid');
    if (grid && M.recommendedVideos) {
      grid.innerHTML = M.recommendedVideos.slice(0, 12).map(v => renderVideoCard(v)).join('');
    }
  }

  function populateSpare() {}

  function populateShortDramaPlayer(params) {
    const dramaId = params && params.id ? parseInt(params.id) : 1;
    const drama = (M.shortDramas || []).find(s => s.id === dramaId) || M.shortDramas[0];
    if (!drama) return;
    const titleEl = $('#shortdrama-player-title'); if (titleEl) titleEl.textContent = drama.title;
    const epGrid = $('#shortdrama-episode-grid');
    if (epGrid) {
      const count = drama.episodeCount || 80;
      const html = [];
      for (let i = 1; i <= Math.min(count, 30); i++) {
        html.push('<span class="episode-item' + (i === 1 ? ' active' : '') + '" data-ep="' + i + '">' + i + '</span>');
      }
      epGrid.innerHTML = html.join('');
    }
  }

  function populateShortPlay(params) {
    const videoId = params && params.id ? parseInt(params.id) : 1;
    const video = (M.shortVideos || []).find(v => v.id === videoId) || M.shortVideos[0];
    if (!video) return;
    const infoEl = $('#shortplay-info');
    if (infoEl) {
      infoEl.innerHTML = '<div style="font-size:var(--font-size-lg);font-weight:700;margin-bottom:var(--space-sm);">' + escapeHtml(video.title) + '</div><div style="font-size:var(--font-size-sm);color:var(--text-secondary);">' + escapeHtml(video.author) + ' · ' + escapeHtml(video.likes) + '赞 · ' + escapeHtml(video.views) + '次播放</div>';
    }
  }

  function populateShortVideoPlaylet(params) {
    const dramaId = params && params.id ? parseInt(params.id) : 1;
    const drama = (M.shortDramas || []).find(s => s.id === dramaId) || M.shortDramas[0];
    if (!drama) return;
    const titleEl = $('#shortvideo-playlet-title'); if (titleEl) titleEl.textContent = drama.title;
    const epGrid = $('#shortvideo-playlet-grid');
    if (epGrid) {
      const count = drama.episodeCount || 80;
      const html = [];
      for (let i = 1; i <= Math.min(count, 24); i++) {
        html.push('<span class="episode-item' + (i === 1 ? ' active' : '') + '" data-ep="' + i + '">' + i + '</span>');
      }
      epGrid.innerHTML = html.join('');
    }
  }

  function populateSearchResult() {
    const list = $('#search-result-list');
    if (!list) return;
    const keyword = ($('#search-input') || {}).value || '';
    const allVideos = [...(M.recommendedVideos || []), ...(M.longVideos || []), ...(M.shortDramas || []), ...(M.comics || [])];
    const filtered = keyword ? allVideos.filter(v => v.title && v.title.includes(keyword)) : allVideos.slice(0, 10);
    if (filtered.length === 0) {
      list.innerHTML = '<div class="empty-state"><div class="empty-icon">🔍</div><div class="empty-text">未找到相关结果</div></div>';
    } else {
      list.innerHTML = filtered.slice(0, 15).map(v => '<div class="search-result-item" data-navigate="video-play/' + (v.id || 1) + '"><div class="result-poster"><img src="' + escapeHtml(v.cover || v.thumbnail || '') + '" alt="' + escapeHtml(v.title) + '" loading="lazy"></div><div class="result-info"><div class="result-title">' + escapeHtml(v.title) + '</div><div class="result-desc">' + escapeHtml(v.description || '') + '</div></div></div>').join('');
    }
  }

  function populatePages() {
    const grid = $('#pages-grid');
    if (!grid) return;
    const pages = [
      { name: '有声书', route: 'audio' },
      { name: '有声排行', route: 'audioRank' },
      { name: '全部剧集', route: 'allDramas' },
      { name: '长视频', route: 'longVideo' },
      { name: '短视频', route: 'shortVideo' },
      { name: '短剧大全', route: 'shortList' },
      { name: '热门视频', route: 'hotVideo' },
      { name: '精选推荐', route: 'elites' },
      { name: '揭秘', route: 'disclose' },
      { name: '海外资讯', route: 'overseasNews' },
      { name: '动漫分类', route: 'comic-category' },
      { name: '动漫排行', route: 'comicRank' },
      { name: '小说分类', route: 'novel-category' },
      { name: '小说排行', route: 'novelRank' },
      { name: '社区论坛', route: 'bbs' },
      { name: '茶社', route: 'tea' },
      { name: '微密圈', route: 'weimi' },
      { name: '明星', route: 'actor' },
      { name: '我的账户', route: 'account' },
      { name: '账户明细', route: 'accountChange' },
      { name: '购买记录', route: 'buyRecord' },
      { name: '解锁记录', route: 'unlockRecords' },
      { name: '分享', route: 'share' },
      { name: '分享记录', route: 'shareRecord' },
      { name: '离线下载', route: 'download' },
      { name: '我的帖子', route: 'myPost' },
      { name: '我的茶帖', route: 'myTeaPost' },
      { name: '收藏小说', route: 'myFavoritesNovels' },
      { name: '收藏音频', route: 'myAudioFavorites' },
      { name: '收藏动漫', route: 'myCommicCollection' },
      { name: '收藏与历史', route: 'home-collect' },
      { name: '观看历史', route: 'home-history' },
      { name: '修改资料', route: 'changeInfo' },
      { name: '修改密码', route: 'changePassword' },
      { name: '帮助中心', route: 'problem' },
      { name: '主题设置', route: 'theme' },
      { name: '备用页面', route: 'spare' },
      { name: '搜索结果', route: 'searchResult' },
      { name: '全部页面', route: 'pages' }
    ];
    grid.innerHTML = pages.map(p => '<div class="video-card" data-navigate="' + p.route + '"><div style="height:100px;background:var(--bg-card);display:flex;align-items:center;justify-content:center;font-size:32px;border-radius:var(--radius-md);">📄</div><div class="card-info"><div class="card-title" style="text-align:center;">' + escapeHtml(p.name) + '</div></div></div>').join('');
  }

  function populateActorList() {
    const grid = $('#actor-grid');
    if (grid && M.actors) {
      grid.innerHTML = M.actors.map(a => '<div class="video-card" data-navigate="actor/' + encodeURIComponent(a.name) + '"><div style="height:120px;background:var(--bg-card);display:flex;align-items:center;justify-content:center;border-radius:var(--radius-md);"><img src="' + escapeHtml(a.avatar) + '" style="width:80px;height:80px;border-radius:50%;object-fit:cover;"></div><div class="card-info"><div class="card-title" style="text-align:center;">' + escapeHtml(a.name) + '</div></div></div>').join('');
    }
  }

  // ==================== 搜索逻辑 ====================

  function updateSearchHistoryUI() {
    const list = $('#search-history-list');
    if (!list) return;
    if (searchHistoryLocal.length === 0) {
      list.innerHTML = '<span style="color:var(--text-placeholder);font-size:var(--font-size-sm);">暂无搜索历史</span>';
      return;
    }
    list.innerHTML = searchHistoryLocal.map(k => `
      <span class="search-history-item" data-keyword="${escapeHtml(k)}">${escapeHtml(k)}</span>
    `).join('');
  }

  function addSearchHistory(keyword) {
    if (!keyword.trim()) return;
    searchHistoryLocal = [keyword, ...searchHistoryLocal.filter(k => k !== keyword)].slice(0, 10);
    try { localStorage.setItem('mg_search_history', JSON.stringify(searchHistoryLocal)); } catch (e) {}
    updateSearchHistoryUI();
  }

  function clearSearchHistory() {
    searchHistoryLocal = [];
    try { localStorage.setItem('mg_search_history', '[]'); } catch (e) {}
    updateSearchHistoryUI();
  }

  function performSearch(keyword) {
    if (!keyword.trim()) return;
    const resultsSection = $('#search-results-section');
    const hotSection = $('#search-hot-section');
    const historySection = $('#search-history-section');
    if (!resultsSection) return;
    const allVideos = [...(M.recommendedVideos || []), ...(M.longVideos || []), ...(M.shortDramas || []), ...(M.comics || [])];
    const filtered = allVideos.filter(v => v.title && v.title.includes(keyword));

    if (hotSection) hotSection.style.display = 'none';
    if (historySection) historySection.style.display = 'none';
    resultsSection.style.display = '';

    if (filtered.length === 0) {
      resultsSection.innerHTML = `<div class="empty-state"><div class="empty-icon">🔍</div><div class="empty-text">未找到"${escapeHtml(keyword)}"相关结果</div></div>`;
    } else {
      resultsSection.innerHTML = `<div style="font-size:var(--font-size-sm);color:var(--text-tertiary);margin-bottom:var(--space-md);">找到 ${filtered.length} 个结果</div>
        ${filtered.slice(0, 15).map(v => `
          <div class="search-result-item" data-navigate="video-play/${v.id || 1}">
            <div class="result-poster"><img src="${escapeHtml(v.cover || v.thumbnail || '')}" alt="${escapeHtml(v.title)}" loading="lazy"></div>
            <div class="result-info">
              <div class="result-title">${escapeHtml(v.title)}</div>
              <div class="result-tags">${(v.tags || v.genreTags || []).slice(0, 3).map(t => `<span class="card-tag">${escapeHtml(t)}</span>`).join('')}</div>
              <div class="result-desc">${escapeHtml(v.description || v.desc || '')}</div>
              <div class="result-meta">${v.score || v.rating ? (v.score || v.rating) + '分' : ''} · ${v.episodeCount ? v.episodeCount + '集' : ''}</div>
            </div>
          </div>
        `).join('')}`;
    }
  }

  function setupSearchEvents() {
    const input = $('#search-input');
    const clearBtn = $('#search-clear');
    const cancelBtn = $('#search-cancel-btn');
    const hotTags = $('#search-hot-tags');
    const historyClear = $('#search-history-clear');
    const historyList = $('#search-history-list');

    if (input) {
      input.addEventListener('input', () => {
        const val = input.value.trim();
        if (clearBtn) clearBtn.classList.toggle('visible', val.length > 0);
        if (val) performSearch(val);
        else {
          const rs = $('#search-results-section'); if (rs) rs.style.display = 'none';
          const hs = $('#search-hot-section'); if (hs) hs.style.display = '';
          const hss = $('#search-history-section'); if (hss) hss.style.display = '';
        }
      });
      input.addEventListener('keydown', (e) => {
        if (e.key === 'Enter') { addSearchHistory(input.value.trim()); }
      });
    }

    if (clearBtn) clearBtn.addEventListener('click', () => { if (input) { input.value = ''; clearBtn.classList.remove('visible'); } });
    if (cancelBtn) cancelBtn.addEventListener('click', goBack);
    if (historyClear) historyClear.addEventListener('click', clearSearchHistory);

    if (hotTags) {
      hotTags.addEventListener('click', (e) => {
        const tag = e.target.closest('.search-hot-tag');
        if (!tag) return;
        const kw = tag.dataset.keyword;
        if (input) input.value = kw;
        addSearchHistory(kw);
        performSearch(kw);
      });
    }
    if (historyList) {
      historyList.addEventListener('click', (e) => {
        const item = e.target.closest('.search-history-item');
        if (!item) return;
        const kw = item.dataset.keyword;
        if (input) input.value = kw;
        addSearchHistory(kw);
        performSearch(kw);
      });
    }

    // 搜索页面每次显示时重新绑定
    if (input && !input.dataset.bound) {
      input.dataset.bound = '1';
    }
  }

  function resetSearchPage() {
    const input = $('#search-input'); if (input) input.value = '';
    const clearBtn = $('#search-clear'); if (clearBtn) clearBtn.classList.remove('visible');
    const rs = $('#search-results-section'); if (rs) rs.style.display = 'none';
    const hs = $('#search-hot-section'); if (hs) hs.style.display = '';
    const hss = $('#search-history-section'); if (hss) hss.style.display = '';
    updateSearchHistoryUI();
  }

  // ==================== 事件处理 ====================

  function setupGlobalEvents() {
    // 委托：data-navigate 点击
    document.addEventListener('click', (e) => {
      const navEl = e.target.closest('[data-navigate]');
      if (navEl) {
        e.preventDefault();
        const target = navEl.dataset.navigate;
        if (target) navigateTo(target);
        return;
      }

      const backEl = e.target.closest('[data-back]');
      if (backEl) {
        e.preventDefault();
        goBack();
        return;
      }
    });

    // Tab bar 点击
    document.addEventListener('click', (e) => {
      const tabItem = e.target.closest('.tab-item[data-tab]');
      if (!tabItem) return;
      e.preventDefault();
      const tab = tabItem.dataset.tab;
      if (tab === 'publish') {
        navigateTo('pages');
        return;
      }
      const tabRoutes = { home: 'home', 'short-drama': 'short-drama', anime: 'anime', novel: 'novel', user: 'user' };
      if (tabRoutes[tab]) navigateTo(tabRoutes[tab]);
    });

    // 页面内 tabs 点击
    document.addEventListener('click', (e) => {
      const tab = e.target.closest('.tabs .tab');
      if (!tab) return;
      const tabsContainer = tab.parentElement;
      if (!tabsContainer) return;
      $$('.tab', tabsContainer).forEach(t => t.classList.remove('active'));
      tab.classList.add('active');
    });

    // 剧集点击
    document.addEventListener('click', (e) => {
      const ep = e.target.closest('.episode-item');
      if (!ep) return;
      const grid = ep.parentElement;
      if (!grid) return;
      $$('.episode-item', grid).forEach(item => item.classList.remove('active'));
      ep.classList.add('active');
      const epNum = ep.dataset.ep;
      showToast(`切换到第${parseInt(epNum) + 1}集`);
    });

    // 播放按钮点击
    document.addEventListener('click', (e) => {
      const playBtn = e.target.closest('#player-play-btn');
      if (!playBtn) return;
      showToast('正在加载播放器...', 'loading');
      const progressBar = $('.player-progress-bar');
      if (progressBar) {
        let p = 0;
        const sim = setInterval(() => {
          p += Math.random() * 8;
          if (p >= 100) { p = 100; clearInterval(sim); }
          progressBar.style.width = p + '%';
          const ct = $('#player-current-time');
          const tt = $('#player-total-time');
          if (ct && tt) {
            const totalParts = tt.textContent.split(':');
            const totalSecs = parseInt(totalParts[0]) * 60 + parseInt(totalParts[1]);
            const curSecs = Math.floor(totalSecs * p / 100);
            const m = String(Math.floor(curSecs / 60)).padStart(2, '0');
            const s = String(curSecs % 60).padStart(2, '0');
            ct.textContent = m + ':' + s;
          }
        }, 800);
        setTimeout(() => { hideToast(); clearInterval(sim); }, 3000);
      }
    });

    // VIP 开通按钮
    document.addEventListener('click', (e) => {
      const vipBtn = e.target.closest('#vip-open-btn');
      if (!vipBtn) return;
      showToast('会员开通功能开发中');
    });

    // 登录按钮
    document.addEventListener('click', (e) => {
      const loginBtn = e.target.closest('#login-phone-btn, #login-wechat-btn');
      if (loginBtn) {
        showToast('登录功能开发中');
        setTimeout(() => { navigateTo('home'); }, 1000);
      }
    });
  }

  // ==================== 初始化 ====================

  function init() {
    // 注入页面
    Object.keys(PAGE_TEMPLATES).forEach(id => {
      if (!document.getElementById(id)) {
        const el = document.createElement('section');
        el.className = 'page';
        el.id = id;
        el.innerHTML = PAGE_TEMPLATES[id];
        pagesContainer.appendChild(el);
      }
    });

    // 注入 tab bar
    if (!document.getElementById('tab-bar')) {
      const barWrap = document.createElement('div');
      barWrap.innerHTML = TAB_BAR_HTML;
      appEl.appendChild(barWrap.firstElementChild);
    }
    tabBarEl = document.getElementById('tab-bar');

    // 路由处理
    window.addEventListener('hashchange', () => {
      const parsed = parseHash();
      handleRoute(parsed.page, parsed.params);
    });

    // 全局事件
    setupGlobalEvents();

    // 搜索页面事件
    setupSearchEvents();

    // 初始路由
    const parsed = parseHash();
    switchPage(parsed.page === 'home' ? 'page-home' : ROUTE_MAP[parsed.page] || DYNAMIC_ROUTES[parsed.page] || 'page-home', parsed.params);

    // 隐藏加载骨架屏
    setTimeout(() => {
      if (loadingEl) loadingEl.style.display = 'none';
    }, 500);

    // 窗口失焦时暂停 swiper
    document.addEventListener('visibilitychange', () => {
      if (document.hidden) {
        if (swiperTimer) clearInterval(swiperTimer);
      } else {
        if (currentPageId === 'page-home') { resetSwiperTimer(); }
      }
    });
  }

  // 启动
  if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', init);
  } else {
    init();
  }

  // ==================== 暴露 API ====================
  window.App = {
    navigateTo,
    goBack,
    showToast,
    hideToast,
    getCurrentPage: () => currentPageId
  };

})();
