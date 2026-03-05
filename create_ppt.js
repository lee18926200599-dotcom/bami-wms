const PptxGenJS = require("pptxgenjs");

// 创建演示文稿
const pptx = new PptxGenJS();
pptx.layout = "LAYOUT_16x9";

// 定义颜色
const PRIMARY_BLUE = "005293";
const ACCENT_BLUE = "0096C7";
const DARK_NAVY = "0A2342";
const WHITE = "FFFFFF";
const LIGHT_GRAY = "F5F5F5";

// ========== 第1页：封面 ==========
let slide1 = pptx.addSlide();
slide1.background = { color: DARK_NAVY };

// 装饰线条
slide1.addShape(pptx.ShapeType.rect, { x: 0.5, y: 2.8, w: 1.5, h: 0.05, fill: { color: ACCENT_BLUE } });
slide1.addShape(pptx.ShapeType.rect, { x: 8, y: 2.8, w: 1.5, h: 0.05, fill: { color: ACCENT_BLUE } });

// 主标题
slide1.addText("八米科技", {
  x: 0.5, y: 2.2, w: 9, h: 1,
  fontSize: 60, bold: true, color: WHITE, align: "center"
});

// 副标题
slide1.addText("跨境供应链数字化解决方案领导者", {
  x: 0.5, y: 3.2, w: 9, h: 0.8,
  fontSize: 28, color: ACCENT_BLUE, align: "center"
});

// 底部条
slide1.addShape(pptx.ShapeType.rect, { x: 0, y: 4.5, w: 10, h: 0.6, fill: { color: PRIMARY_BLUE } });

// ========== 第2页：公司简介 ==========
let slide2 = pptx.addSlide();
slide2.addShape(pptx.ShapeType.rect, { x: 0, y: 0, w: 10, h: 0.9, fill: { color: PRIMARY_BLUE } });
slide2.addText("公司简介", { x: 0.3, y: 0.15, w: 9, h: 0.6, fontSize: 32, bold: true, color: WHITE });

const companyInfo = [
  { label: "公司全称", value: "广州八米网络科技有限公司" },
  { label: "品牌名称", value: "八米科技（BAMIKEJI）" },
  { label: "官方网站", value: "www.bamitms.com" },
  { label: "企业定位", value: "中国领先的跨境供应链数字化解决方案服务商" },
  { label: "行业经验", value: "15年国际物流行业深耕，服务1200+成功客户" },
  { label: "企业荣誉", value: "2025年入选广东省科技型中小企业库" },
];

let yPos = 1.2;
companyInfo.forEach((item, idx) => {
  // 标签
  slide2.addText(item.label + ":", {
    x: 0.4, y: yPos, w: 2, h: 0.4,
    fontSize: 16, bold: true, color: PRIMARY_BLUE
  });
  // 值
  slide2.addText(item.value, {
    x: 2.5, y: yPos, w: 7, h: 0.4,
    fontSize: 16, color: "333333"
  });
  yPos += 0.55;
});

// ========== 第3页：核心业务 ==========
let slide3 = pptx.addSlide();
slide3.addShape(pptx.ShapeType.rect, { x: 0, y: 0, w: 10, h: 0.9, fill: { color: PRIMARY_BLUE } });
slide3.addText("核心业务定位", { x: 0.3, y: 0.15, w: 9, h: 0.6, fontSize: 32, bold: true, color: WHITE });

const coreBusiness = [
  "专注为国际物流、集运、海外仓企业提供软件系统支撑",
  "通过数字化手段帮助传统跨境物流企业实现转型升级",
  "构建「获客+集运+代购+商城」一体化管理生态",
  "赋能中国品牌出海，打通端到端供应链业务体系",
];

yPos = 1.3;
coreBusiness.forEach((text, idx) => {
  slide3.addText("• " + text, {
    x: 0.5, y: yPos, w: 9, h: 0.6,
    fontSize: 20, color: "333333"
  });
  yPos += 0.8;
});

// ========== 第4页：四大产品 ==========
let slide4 = pptx.addSlide();
slide4.background = { color: "E6F5FF" };
slide4.addShape(pptx.ShapeType.rect, { x: 0, y: 0, w: 10, h: 1, fill: { color: DARK_NAVY } });
slide4.addText("四大核心产品", { x: 0.3, y: 0.2, w: 9, h: 0.7, fontSize: 36, bold: true, color: WHITE, align: "center" });

const products = [
  { name: "代购集运系统", desc: "独家研发的代购商城与集运一体化系统，支持纯集运业务场景。帮助海外华人实现反向海淘。" },
  { name: "跨境TMS系统", desc: "适合海陆空铁运输、集装转运、自营国际专线。从工厂到客户、跨系统跨部门全链条打通。" },
  { name: "海外仓WMS系统", desc: "助力海外仓企业搭建全球化仓库布局，支持代发货、虚拟仓、退换标、中转调拨等业务。" },
  { name: "集成供应链", desc: "建设企业面向客户产品交付的端到端业务体系，为全球供应链全领域提供数字化协同。" },
];

// 2x2 卡片布局
const cardPositions = [
  { x: 0.3, y: 1.3 }, { x: 5.2, y: 1.3 },
  { x: 0.3, y: 3.0 }, { x: 5.2, y: 3.0 }
];

products.forEach((prod, idx) => {
  const pos = cardPositions[idx];
  // 卡片背景
  slide4.addShape(pptx.ShapeType.roundRect, { x: pos.x, y: pos.y, w: 4.4, h: 1.5, fill: { color: WHITE }, line: { color: ACCENT_BLUE, width: 2 } });
  // 顶部色条
  slide4.addShape(pptx.ShapeType.rect, { x: pos.x, y: pos.y, w: 4.4, h: 0.35, fill: { color: PRIMARY_BLUE } });
  // 产品名称
  slide4.addText(prod.name, { x: pos.x + 0.1, y: pos.y + 0.05, w: 4.2, h: 0.3, fontSize: 18, bold: true, color: WHITE });
  // 描述
  slide4.addText(prod.desc, { x: pos.x + 0.15, y: pos.y + 0.45, w: 4.1, h: 1, fontSize: 12, color: "333333" });
});

// ========== 第5页：核心优势 ==========
let slide5 = pptx.addSlide();
slide5.addText("核心竞争优势", { x: 0.3, y: 0.3, w: 9, h: 0.8, fontSize: 40, bold: true, color: DARK_NAVY, align: "center" });
slide5.addShape(pptx.ShapeType.rect, { x: 4, y: 1.0, w: 2, h: 0.05, fill: { color: ACCENT_BLUE } });

const advantages = [
  { num: "15年", desc: "行业沉淀经验" },
  { num: "80%", desc: "技术+服务人员占比" },
  { num: "1200+", desc: "成功客户案例" },
  { num: "7×24", desc: "小时客服在线" },
  { num: "首创", desc: "多组织+多仓+联盟模式" },
  { num: "百万票次", desc: "年订单系统考验" },
  { num: "定制开发", desc: "灵活可拓展" },
  { num: "1亿+", desc: "商品池资源" },
];

const advPositions = [
  { x: 0.4, y: 1.4 }, { x: 2.6, y: 1.4 }, { x: 4.8, y: 1.4 }, { x: 7.0, y: 1.4 },
  { x: 0.4, y: 3.2 }, { x: 2.6, y: 3.2 }, { x: 4.8, y: 3.2 }, { x: 7.0, y: 3.2 },
];

advantages.forEach((adv, idx) => {
  const pos = advPositions[idx];
  // 圆形
  slide5.addShape(pptx.ShapeType.ellipse, { x: pos.x + 0.3, y: pos.y, w: 1.2, h: 1.2, fill: { color: PRIMARY_BLUE } });
  // 数字
  slide5.addText(adv.num, { x: pos.x, y: pos.y + 0.35, w: 1.8, h: 0.5, fontSize: 20, bold: true, color: WHITE, align: "center" });
  // 描述
  slide5.addText(adv.desc, { x: pos.x, y: pos.y + 1.3, w: 1.8, h: 0.5, fontSize: 12, color: "333333", align: "center" });
});

// ========== 第6页：服务模式 ==========
let slide6 = pptx.addSlide();
slide6.addShape(pptx.ShapeType.rect, { x: 0, y: 0, w: 10, h: 0.9, fill: { color: PRIMARY_BLUE } });
slide6.addText("服务模式", { x: 0.3, y: 0.15, w: 9, h: 0.6, fontSize: 32, bold: true, color: WHITE });

const services = [
  { title: "标准化SaaS产品", desc: "开箱即用，按年/月付费，功能成熟，快速上线。适合中小型物流企业。" },
  { title: "定制化开发服务", desc: "根据企业特殊需求深度定制，打造专属系统。适合大型客户或特殊业务场景。" },
  { title: "7×24小时客服支持", desc: "强大客服团队全天候在线，确保系统稳定运行。" },
  { title: "持续升级迭代", desc: "紧跟行业发展，持续产品升级和功能迭代，确保客户始终使用最先进系统。" },
];

yPos = 1.2;
services.forEach((svc, idx) => {
  slide6.addText("▶ " + svc.title, { x: 0.4, y: yPos, w: 9, h: 0.4, fontSize: 18, bold: true, color: PRIMARY_BLUE });
  slide6.addText(svc.desc, { x: 0.7, y: yPos + 0.4, w: 8.5, h: 0.5, fontSize: 14, color: "333333" });
  yPos += 1.0;
});

// ========== 第7页：目标客户 ==========
let slide7 = pptx.addSlide();
slide7.addShape(pptx.ShapeType.rect, { x: 0, y: 0, w: 10, h: 0.9, fill: { color: PRIMARY_BLUE } });
slide7.addText("目标客户群体", { x: 0.3, y: 0.15, w: 9, h: 0.6, fontSize: 32, bold: true, color: WHITE });

const clients = [
  "国际货运代理企业",
  "华人集运公司（反向海淘服务商）",
  "自营国际专线物流企业",
  "海外仓运营商（一件代发、FBA中转）",
  "跨境电商平台",
  "品牌出海企业",
  "代购平台及个人代购服务商",
];

yPos = 1.2;
clients.forEach((text, idx) => {
  slide7.addText("• " + text, { x: 0.5, y: yPos, w: 9, h: 0.5, fontSize: 20, color: "333333" });
  yPos += 0.55;
});

// ========== 第8页：代表客户 ==========
let slide8 = pptx.addSlide();
slide8.background = { color: DARK_NAVY };
slide8.addText("代表客户", { x: 0.3, y: 0.3, w: 9, h: 0.8, fontSize: 36, bold: true, color: WHITE, align: "center" });

const clientNames = [
  "中欧联运", "恒发国际", "广州海淘集运",
  "俄罗斯涅瓦物流", "四季安集团", "弘海铁盛",
  "泛昇国际", "嘉拓智能", "广东晟晖再生资源集团"
];

const clientPositions = [
  { x: 0.5, y: 1.3 }, { x: 3.7, y: 1.3 }, { x: 6.9, y: 1.3 },
  { x: 0.5, y: 2.2 }, { x: 3.7, y: 2.2 }, { x: 6.9, y: 2.2 },
  { x: 0.5, y: 3.1 }, { x: 3.7, y: 3.1 }, { x: 6.9, y: 3.1 },
];

clientNames.forEach((name, idx) => {
  const pos = clientPositions[idx];
  slide8.addShape(pptx.ShapeType.roundRect, { x: pos.x, y: pos.y, w: 2.5, h: 0.6, fill: { color: PRIMARY_BLUE } });
  slide8.addText(name, { x: pos.x, y: pos.y + 0.15, w: 2.5, h: 0.4, fontSize: 16, bold: true, color: WHITE, align: "center" });
});

// ========== 第9页：最新动态 ==========
let slide9 = pptx.addSlide();
slide9.addShape(pptx.ShapeType.rect, { x: 0, y: 0, w: 10, h: 0.9, fill: { color: PRIMARY_BLUE } });
slide9.addText("最新动态", { x: 0.3, y: 0.15, w: 9, h: 0.6, fontSize: 32, bold: true, color: WHITE });

const news = [
  { date: "2026年3月", content: "与菲律宾最大零售IT服务商 ASC 签署长期战略合作协议，联合打造东南亚统仓共配4PL解决方案" },
  { date: "2025年12月", content: "成功入选广东省科技型中小企业库，获得科技部门高度认可" },
  { date: "2025年", content: "与新能源高端装备领军企业嘉拓智能达成战略合作，进军新能源智能制造物流领域" },
  { date: "2025年6月", content: "与广东晟晖再生资源集团合作，建设供应链数智化平台，进军再生资源行业" },
];

yPos = 1.2;
news.forEach((item, idx) => {
  slide9.addText(item.date, { x: 0.4, y: yPos, w: 1.5, h: 0.4, fontSize: 16, bold: true, color: PRIMARY_BLUE });
  slide9.addText(item.content, { x: 2.0, y: yPos, w: 7.5, h: 0.8, fontSize: 14, color: "333333" });
  yPos += 0.9;
});

// ========== 第10页：结束页 ==========
let slide10 = pptx.addSlide();
slide10.background = { color: PRIMARY_BLUE };

slide10.addText("广州八米网络科技有限公司", { x: 0.5, y: 1.5, w: 9, h: 1, fontSize: 36, bold: true, color: WHITE, align: "center" });
slide10.addText("八米科技  BAMIKEJI", { x: 0.5, y: 2.5, w: 9, h: 0.8, fontSize: 28, color: ACCENT_BLUE, align: "center" });
slide10.addText("www.bamitms.com", { x: 0.5, y: 3.3, w: 9, h: 0.6, fontSize: 24, color: WHITE, align: "center" });
slide10.addText("15年行业经验 · 1200+成功客户 · 让跨境物流更智能", { x: 0.5, y: 4.2, w: 9, h: 0.5, fontSize: 16, color: WHITE, align: "center" });
slide10.addShape(pptx.ShapeType.rect, { x: 3.5, y: 4.8, w: 3, h: 0.05, fill: { color: ACCENT_BLUE } });

// 保存文件
const outputPath = require('path').join(require('os').homedir(), 'Desktop', '八米科技企业介绍.pptx');
pptx.writeFile({ fileName: outputPath })
  .then(() => console.log('PPT已生成：' + outputPath))
  .catch(err => console.error('生成失败：', err));