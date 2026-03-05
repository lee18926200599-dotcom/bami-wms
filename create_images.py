from PIL import Image, ImageDraw, ImageFont
import os

def create_slide(width=1280, height=720):
    return Image.new('RGB', (width, height), 'white'), ImageDraw.Draw(Image.new('RGB', (width, height), 'white'))

def draw_rounded_rect(draw, xy, radius, fill, outline=None, width=1):
    x1, y1, x2, y2 = xy
    draw.rounded_rectangle(xy, radius=radius, fill=fill, outline=outline, width=width)

def get_font(size, bold=False):
    # Try different fonts
    fonts = [
        '/System/Library/Fonts/PingFang.ttc',
        '/System/Library/Fonts/STHeiti Light.ttc',
        '/System/Library/Fonts/Helvetica.ttc',
        '/usr/share/fonts/truetype/dejavu/DejaVuSans-Bold.ttf',
    ]
    for font_path in fonts:
        if os.path.exists(font_path):
            try:
                return ImageFont.truetype(font_path, size)
            except:
                continue
    return ImageFont.load_default()

# 创建输出目录
output_dir = os.path.expanduser("~/Desktop/ppt_slides")
os.makedirs(output_dir, exist_ok=True)

# 颜色定义
DARK_NAVY = (10, 35, 66)
PRIMARY_BLUE = (0, 82, 147)
ACCENT_BLUE = (0, 150, 199)
WHITE = (255, 255, 255)
LIGHT_BLUE = (230, 245, 255)

def slide_1_cover():
    img = Image.new('RGB', (1280, 720), DARK_NAVY)
    draw = ImageDraw.Draw(img)
    
    # 装饰线条
    draw.rectangle([50, 280, 170, 284], fill=ACCENT_BLUE)
    draw.rectangle([1110, 280, 1230, 284], fill=ACCENT_BLUE)
    
    # 标题
    title_font = get_font(72, bold=True)
    draw.text((640, 250), "八米科技", font=title_font, fill=WHITE, anchor="mm")
    
    # 副标题
    subtitle_font = get_font(32)
    draw.text((640, 340), "跨境供应链数字化解决方案领导者", font=subtitle_font, fill=ACCENT_BLUE, anchor="mm")
    
    # 底部条
    draw.rectangle([0, 660, 1280, 720], fill=PRIMARY_BLUE)
    
    img.save(f"{output_dir}/slide_01_cover.png")
    print("Slide 1 created")

def slide_2_company():
    img = Image.new('RGB', (1280, 720), WHITE)
    draw = ImageDraw.Draw(img)
    
    # 顶部条
    draw.rectangle([0, 0, 1280, 80], fill=PRIMARY_BLUE)
    
    # 标题
    title_font = get_font(36, bold=True)
    draw.text((30, 20), "公司简介", font=title_font, fill=WHITE)
    
    # 内容
    content = [
        ("公司全称", "广州八米网络科技有限公司"),
        ("品牌名称", "八米科技（BAMIKEJI）"),
        ("官方网站", "www.bamitms.com"),
        ("企业定位", "中国领先的跨境供应链数字化解决方案服务商"),
        ("行业经验", "15年国际物流行业深耕，服务1200+成功客户"),
        ("企业荣誉", "2025年入选广东省科技型中小企业库"),
    ]
    
    label_font = get_font(18, bold=True)
    value_font = get_font(18)
    y = 110
    for label, value in content:
        draw.text((40, y), f"{label}：", font=label_font, fill=PRIMARY_BLUE)
        draw.text((250, y), value, font=value_font, fill=(51, 51, 51))
        y += 70
    
    img.save(f"{output_dir}/slide_02_company.png")
    print("Slide 2 created")

def slide_3_business():
    img = Image.new('RGB', (1280, 720), WHITE)
    draw = ImageDraw.Draw(img)
    
    draw.rectangle([0, 0, 1280, 80], fill=PRIMARY_BLUE)
    title_font = get_font(36, bold=True)
    draw.text((30, 20), "核心业务定位", font=title_font, fill=WHITE)
    
    items = [
        "专注为国际物流、集运、海外仓企业提供软件系统支撑",
        "通过数字化手段帮助传统跨境物流企业实现转型升级",
        "构建「获客+集运+代购+商城」一体化管理生态",
        "赋能中国品牌出海，打通端到端供应链业务体系",
    ]
    
    item_font = get_font(24)
    y = 130
    for item in items:
        draw.text((50, y), f"• {item}", font=item_font, fill=(51, 51, 51))
        y += 90
    
    img.save(f"{output_dir}/slide_03_business.png")
    print("Slide 3 created")

def slide_4_products():
    img = Image.new('RGB', (1280, 720), LIGHT_BLUE)
    draw = ImageDraw.Draw(img)
    
    draw.rectangle([0, 0, 1280, 90], fill=DARK_NAVY)
    title_font = get_font(42, bold=True)
    draw.text((640, 25), "四大核心产品", font=title_font, fill=WHITE, anchor="mm")
    
    products = [
        ("代购集运系统", "独家研发的代购商城与集运一体化系统，支持纯集运业务场景，帮助海外华人实现反向海淘。"),
        ("跨境TMS系统", "适合海陆空铁运输、集装转运、自营国际专线。从工厂到客户、跨系统跨部门全链条打通。"),
        ("海外仓WMS系统", "助力海外仓企业搭建全球化仓库布局，支持代发货、虚拟仓、退换标、中转调拨等业务。"),
        ("集成供应链", "建设端到端业务体系，为全球供应链全领域提供数字化协同，赋能中国品牌出海。"),
    ]
    
    positions = [(40, 120), (660, 120), (40, 390), (660, 390)]
    name_font = get_font(20, bold=True)
    desc_font = get_font(14)
    
    for i, (name, desc) in enumerate(products):
        x, y = positions[i]
        # 卡片背景
        draw.rounded_rectangle([x, y, x+580, y+240], radius=10, fill=WHITE, outline=ACCENT_BLUE, width=2)
        # 顶部色条
        draw.rectangle([x, y, x+580, y+45], fill=PRIMARY_BLUE)
        # 产品名称
        draw.text((x+15, y+10), name, font=name_font, fill=WHITE)
        # 描述
        draw.text((x+20, y+60), desc, font=desc_font, fill=(51, 51, 51))
    
    img.save(f"{output_dir}/slide_04_products.png")
    print("Slide 4 created")

def slide_5_advantages():
    img = Image.new('RGB', (1280, 720), WHITE)
    draw = ImageDraw.Draw(img)
    
    title_font = get_font(48, bold=True)
    draw.text((640, 40), "核心竞争优势", font=title_font, fill=DARK_NAVY, anchor="mm")
    draw.rectangle([540, 100, 740, 104], fill=ACCENT_BLUE)
    
    advantages = [
        ("15年", "行业沉淀经验"),
        ("80%", "技术+服务人员占比"),
        ("1200+", "成功客户案例"),
        ("7×24", "小时客服在线"),
        ("首创", "多组织+多仓+联盟模式"),
        ("百万票次", "年订单系统考验"),
        ("定制开发", "灵活可拓展"),
        ("1亿+", "商品池资源"),
    ]
    
    positions = [
        (50, 150), (330, 150), (610, 150), (890, 150),
        (50, 380), (330, 380), (610, 380), (890, 380)
    ]
    
    num_font = get_font(22, bold=True)
    desc_font = get_font(13)
    
    for i, (num, desc) in enumerate(advantages):
        x, y = positions[i]
        # 圆形
        draw.ellipse([x+40, y, x+160, y+120], fill=PRIMARY_BLUE)
        # 数字
        draw.text((x+100, y+45), num, font=num_font, fill=WHITE, anchor="mm")
        # 描述
        draw.text((x+100, y+135), desc, font=desc_font, fill=(51, 51, 51), anchor="mm")
    
    img.save(f"{output_dir}/slide_05_advantages.png")
    print("Slide 5 created")

def slide_6_service():
    img = Image.new('RGB', (1280, 720), WHITE)
    draw = ImageDraw.Draw(img)
    
    draw.rectangle([0, 0, 1280, 80], fill=PRIMARY_BLUE)
    title_font = get_font(36, bold=True)
    draw.text((30, 20), "服务模式", font=title_font, fill=WHITE)
    
    services = [
        ("标准化SaaS产品", "开箱即用，按年/月付费，功能成熟，快速上线。适合中小型物流企业。"),
        ("定制化开发服务", "根据企业特殊需求深度定制，打造专属系统。适合大型客户或特殊业务场景。"),
        ("7×24小时客服支持", "强大客服团队全天候在线，确保系统稳定运行。"),
        ("持续升级迭代", "紧跟行业发展，持续产品升级和功能迭代。"),
    ]
    
    title_font2 = get_font(18, bold=True)
    desc_font = get_font(14)
    y = 110
    
    for title, desc in services:
        draw.text((40, y), f"▶ {title}", font=title_font2, fill=PRIMARY_BLUE)
        draw.text((70, y+35), desc, font=desc_font, fill=(51, 51, 51))
        y += 110
    
    img.save(f"{output_dir}/slide_06_service.png")
    print("Slide 6 created")

def slide_7_clients():
    img = Image.new('RGB', (1280, 720), WHITE)
    draw = ImageDraw.Draw(img)
    
    draw.rectangle([0, 0, 1280, 80], fill=PRIMARY_BLUE)
    title_font = get_font(36, bold=True)
    draw.text((30, 20), "目标客户群体", font=title_font, fill=WHITE)
    
    clients = [
        "国际货运代理企业",
        "华人集运公司（反向海淘服务商）",
        "自营国际专线物流企业",
        "海外仓运营商（一件代发、FBA中转）",
        "跨境电商平台",
        "品牌出海企业",
        "代购平台及个人代购服务商",
    ]
    
    client_font = get_font(22)
    y = 110
    for client in clients:
        draw.text((50, y), f"• {client}", font=client_font, fill=(51, 51, 51))
        y += 65
    
    img.save(f"{output_dir}/slide_07_clients.png")
    print("Slide 7 created")

def slide_8_partners():
    img = Image.new('RGB', (1280, 720), DARK_NAVY)
    draw = ImageDraw.Draw(img)
    
    title_font = get_font(42, bold=True)
    draw.text((640, 50), "代表客户", font=title_font, fill=WHITE, anchor="mm")
    
    partners = ["中欧联运", "恒发国际", "广州海淘集运", "俄罗斯涅瓦物流", "四季安集团", "弘海铁盛", "泛昇国际", "嘉拓智能", "广东晟晖再生资源集团"]
    
    positions = [
        (60, 140), (450, 140), (840, 140),
        (60, 240), (450, 240), (840, 240),
        (60, 340), (450, 340), (840, 340)
    ]
    
    name_font = get_font(18, bold=True)
    
    for i, partner in enumerate(partners):
        x, y = positions[i]
        draw.rounded_rectangle([x, y, x+380, y+60], radius=8, fill=PRIMARY_BLUE)
        draw.text((x+190, y+18), partner, font=name_font, fill=WHITE, anchor="mm")
    
    img.save(f"{output_dir}/slide_08_partners.png")
    print("Slide 8 created")

def slide_9_news():
    img = Image.new('RGB', (1280, 720), WHITE)
    draw = ImageDraw.Draw(img)
    
    draw.rectangle([0, 0, 1280, 80], fill=PRIMARY_BLUE)
    title_font = get_font(36, bold=True)
    draw.text((30, 20), "最新动态", font=title_font, fill=WHITE)
    
    news = [
        ("2026年3月", "与菲律宾最大零售IT服务商ASC签署长期战略合作协议，联合打造东南亚统仓共配4PL解决方案"),
        ("2025年12月", "成功入选广东省科技型中小企业库，获得科技部门高度认可"),
        ("2025年", "与新能源高端装备领军企业嘉拓智能达成战略合作，进军新能源智能制造物流领域"),
        ("2025年6月", "与广东晟晖再生资源集团合作，建设供应链数智化平台，进军再生资源行业"),
    ]
    
    date_font = get_font(16, bold=True)
    content_font = get_font(14)
    y = 110
    
    for date, content in news:
        draw.text((40, y), date, font=date_font, fill=PRIMARY_BLUE)
        draw.text((200, y), content, font=content_font, fill=(51, 51, 51))
        y += 100
    
    img.save(f"{output_dir}/slide_09_news.png")
    print("Slide 9 created")

def slide_10_end():
    img = Image.new('RGB', (1280, 720), PRIMARY_BLUE)
    draw = ImageDraw.Draw(img)
    
    company_font = get_font(40, bold=True)
    draw.text((640, 200), "广州八米网络科技有限公司", font=company_font, fill=WHITE, anchor="mm")
    
    brand_font = get_font(28)
    draw.text((640, 300), "八米科技  BAMIKEJI", font=brand_font, fill=ACCENT_BLUE, anchor="mm")
    
    web_font = get_font(24)
    draw.text((640, 380), "www.bamitms.com", font=web_font, fill=WHITE, anchor="mm")
    
    tag_font = get_font(16)
    draw.text((640, 480), "15年行业经验 · 1200+成功客户 · 让跨境物流更智能", font=tag_font, fill=WHITE, anchor="mm")
    
    draw.rectangle([480, 560, 800, 565], fill=ACCENT_BLUE)
    
    img.save(f"{output_dir}/slide_10_end.png")
    print("Slide 10 created")

# 生成所有幻灯片
slide_1_cover()
slide_2_company()
slide_3_business()
slide_4_products()
slide_5_advantages()
slide_6_service()
slide_7_clients()
slide_8_partners()
slide_9_news()
slide_10_end()

print(f"\n所有幻灯片已保存到: {output_dir}")