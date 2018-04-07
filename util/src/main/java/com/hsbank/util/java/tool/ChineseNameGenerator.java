package com.hsbank.util.java.tool;

import com.hsbank.util.java.type.NumberUtil;

/**
 * 中文名字随机生成器
 * @author xwy
 * 2011-04-22
 */
public class ChineseNameGenerator {
	/**当前类对象*/
    private static ChineseNameGenerator _instance = null;
    /**姓数组的大小*/
    private final int _xingSize = 95;
    /**名数组的大小*/
    private final int _mingSize = 79;
	/**姓*/
	String[] _xingArray = null;
	/**名*/
	String[] _mingArray = null;
	
	/**
	 * 测试入口
	 * @param args
	 */
	public static void main(String[] args) {
		//随机生成100个中文名字
		for (int i = 0; i < 100; i++) {
			System.out.println(ChineseNameGenerator.getInstance().getRandomName());
		}
	}
	
	/**
	 * 得到当前类唯一的实例
	 * @return
	 */
	public static synchronized ChineseNameGenerator getInstance() {
		return _instance == null ? _instance = new ChineseNameGenerator() : _instance;
	}
	
	/**定义一个私有的构造函数*/
	private ChineseNameGenerator() {
		//姓
		_xingArray = new String[_xingSize];
		_xingArray[0]="白|bai";
		_xingArray[1]="白|bai";
		_xingArray[2]="蔡|cai";
		_xingArray[3]="曹|cao";
		_xingArray[4]="陈|chen";
		_xingArray[5]="戴|dai";
		_xingArray[6]="窦|dou";
		_xingArray[7]="邓|deng";
		_xingArray[8]="狄|di";
		_xingArray[9]="杜|du";
		_xingArray[10]="段|duan";
		_xingArray[11]="范|fan";
		_xingArray[12]="樊|fan";
		_xingArray[13]="房|fang";
		_xingArray[14]="风|feng";
		_xingArray[15]="符|fu";
		_xingArray[16]="福|fu";
		_xingArray[17]="高|gao";
		_xingArray[18]="古|gu";
		_xingArray[19]="关|guan";
		_xingArray[20]="郭|guo";
		_xingArray[21]="毛|mao";
		_xingArray[22]="韩|han";
		_xingArray[23]="胡|hu";
		_xingArray[24]="花|hua";
		_xingArray[25]="洪|hong";
		_xingArray[26]="侯|hou";
		_xingArray[27]="黄|huang";
		_xingArray[28]="贾|jia";
		_xingArray[29]="蒋|jiang";
		_xingArray[30]="金|jin";
		_xingArray[31]="廖|liao";
		_xingArray[32]="梁|liang";
		_xingArray[33]="李|li";
		_xingArray[34]="林|lin";
		_xingArray[35]="刘|liu";
		_xingArray[36]="龙|long";
		_xingArray[37]="陆|lu";
		_xingArray[38]="卢|lu";
		_xingArray[39]="罗|luo";
		_xingArray[40]="马|ma";
		_xingArray[41]="牛|niu";
		_xingArray[42]="庞|pang";
		_xingArray[43]="裴|pei";
		_xingArray[44]="彭|peng";
		_xingArray[45]="戚|qi";
		_xingArray[46]="齐|qi";
		_xingArray[47]="钱|qian";
		_xingArray[48]="乔|qiao";
		_xingArray[49]="秦|qin";
		_xingArray[50]="邱|qiu";
		_xingArray[51]="裘|qiu";
		_xingArray[52]="仇|qiu";
		_xingArray[53]="沙|sha";
		_xingArray[54]="商|shang";
		_xingArray[55]="尚|shang";
		_xingArray[56]="邵|shao";
		_xingArray[57]="沈|shen";
		_xingArray[58]="师|shi";
		_xingArray[59]="施|shi";
		_xingArray[60]="宋|song";
		_xingArray[61]="孙|sun";
		_xingArray[62]="童|tong";
		_xingArray[63]="万|wan";
		_xingArray[64]="王|wang";
		_xingArray[65]="魏|wei";
		_xingArray[66]="卫|wei";
		_xingArray[67]="吴|wu";
		_xingArray[68]="武|wu";
		_xingArray[69]="萧|xiao";
		_xingArray[70]="肖|xiao";
		_xingArray[71]="项|xiang";
		_xingArray[72]="许|xu";
		_xingArray[73]="徐|xu";
		_xingArray[74]="薛|xue";
		_xingArray[75]="杨|yang";
		_xingArray[76]="羊|yang";
		_xingArray[77]="阳|yang";
		_xingArray[78]="易|yi";
		_xingArray[79]="尹|yin";
		_xingArray[80]="俞|yu";
		_xingArray[81]="赵|zhao";
		_xingArray[82]="钟|zhong";
		_xingArray[83]="周|zhou";
		_xingArray[84]="郑|zheng";
		_xingArray[85]="朱|zhu";
		_xingArray[86]="东方|dongfang";
		_xingArray[87]="独孤|dugu";
		_xingArray[88]="慕容|murong";
		_xingArray[89]="欧阳|ouyang";
		_xingArray[90]="司马|sima";
		_xingArray[91]="西门|ximen";
		_xingArray[92]="尉迟|yuchi";
		_xingArray[93]="长孙|zhangsun";
		_xingArray[94]="诸葛|zhuge";
		//名
		_mingArray = new String[_mingSize];
		_mingArray[0]="ai|皑艾哀";
		_mingArray[1]="an|安黯谙";
		_mingArray[2]="ao|奥傲敖骜翱";
		_mingArray[3]="ang|昂盎";
		_mingArray[4]="ba|罢霸";
		_mingArray[5]="bai|白佰";
		_mingArray[6]="ban|斑般";
		_mingArray[7]="bang|邦";
		_mingArray[8]="bei|北倍贝备";
		_mingArray[9]="biao|表标彪飚飙";
		_mingArray[10]="bian|边卞弁忭";
		_mingArray[11]="bu|步不";
		_mingArray[12]="cao|曹草操漕";
		_mingArray[13]="cang|苍仓";
		_mingArray[14]="chang|常长昌敞玚";
		_mingArray[15]="chi|迟持池赤尺驰炽";
		_mingArray[16]="ci|此次词茨辞慈";
		_mingArray[17]="du|独都";
		_mingArray[18]="dong|东侗";
		_mingArray[19]="dou|都";
		_mingArray[20]="fa|发乏珐";
		_mingArray[21]="fan|范凡反泛帆蕃";
		_mingArray[22]="fang|方访邡昉";
		_mingArray[23]="feng|风凤封丰奉枫峰锋";
		_mingArray[24]="fu|夫符弗芙";
		_mingArray[25]="gao|高皋郜镐";
		_mingArray[26]="hong|洪红宏鸿虹泓弘";
		_mingArray[27]="hu|虎忽湖护乎祜浒怙";
		_mingArray[28]="hua|化花华骅桦";
		_mingArray[29]="hao|号浩皓蒿浩昊灏淏";
		_mingArray[30]="ji|积极济技击疾及基集记纪季继吉计冀祭际籍绩忌寂霁稷玑芨蓟戢佶奇诘笈畿犄";
		_mingArray[31]="jian|渐剑见建间柬坚俭";
		_mingArray[32]="kan|刊戡";
		_mingArray[33]="ke|可克科刻珂恪溘牁";
		_mingArray[34]="lang|朗浪廊琅阆莨";
		_mingArray[35]="li|历离里理利立力丽礼黎栗荔沥栎璃";
		_mingArray[36]="lin|临霖林琳";
		_mingArray[37]="ma|马";
		_mingArray[38]="mao|贸冒貌冒懋矛卯瑁";
		_mingArray[39]="miao|淼渺邈";
		_mingArray[40]="nan|楠南";
		_mingArray[41]="pian|片翩";
		_mingArray[42]="qian|潜谦倩茜乾虔千";
		_mingArray[43]="qiang|强羌锖玱";
		_mingArray[44]="qin|亲琴钦沁芩矜";
		_mingArray[45]="qing|清庆卿晴";
		_mingArray[46]="ran|冉然染燃";
		_mingArray[47]="ren|仁刃壬仞";
		_mingArray[48]="sha|沙煞";
		_mingArray[49]="shang|上裳商";
		_mingArray[50]="shen|深审神申慎参莘";
		_mingArray[51]="shi|师史石时十世士诗始示适炻";
		_mingArray[52]="shui|水";
		_mingArray[53]="si|思斯丝司祀嗣巳";
		_mingArray[54]="song|松颂诵";
		_mingArray[55]="tang|堂唐棠瑭";
		_mingArray[56]="tong|统通同童彤仝";
		_mingArray[57]="tian|天田忝";
		_mingArray[58]="wan|万宛晚";
		_mingArray[59]="wei|卫微伟维威韦纬炜惟玮为";
		_mingArray[60]="wu|吴物务武午五巫邬兀毋戊";
		_mingArray[61]="xi|西席锡洗夕兮熹惜";
		_mingArray[62]="xiao|潇萧笑晓肖霄骁校";
		_mingArray[63]="xiong|熊雄";
		_mingArray[64]="yang|羊洋阳漾央秧炀飏鸯";
		_mingArray[65]="yi|易意依亦伊夷倚毅义宜仪艺译翼逸忆怡熠沂颐奕弈懿翊轶屹猗翌";
		_mingArray[66]="yin|隐因引银音寅吟胤訚烟荫";
		_mingArray[67]="ying|映英影颖瑛应莹郢鹰";
		_mingArray[68]="you|幽悠右忧猷酉";
		_mingArray[69]="yu|渔郁寓于余玉雨语预羽舆育宇禹域誉瑜屿御渝毓虞禺豫裕钰煜聿";
		_mingArray[70]="zhi|制至值知质致智志直治执止置芝旨峙芷挚郅炙雉帜";
		_mingArray[71]="zhong|中忠钟衷";
		_mingArray[72]="zhou|周州舟胄繇昼";
		_mingArray[73]="zhu|竹主驻足朱祝诸珠著竺";
		_mingArray[74]="zhuo|卓灼灼拙琢濯斫擢焯酌";
		_mingArray[75]="zi|子资兹紫姿孜梓秭";
		_mingArray[76]="zong|宗枞";
		_mingArray[77]="zu|足族祖卒";
		_mingArray[78]="zuo|作左佐笮凿";
	}
	
	/**
	 * 得到一个随机的姓名，如: 司马忠|simazhong
	 * @return
	 */
	public String getRandomName() {
		//汉字
		String hanzi = "";
		//拼音
		String pingying = "";
		//------<1>.得到一个随机的姓
		String xing = getRandomXing();
		String[] xingArray = xing.split("\\|");
		hanzi += xingArray[0];
		pingying += xingArray[1];
		//让两个字的名字出现的频率高一些
		int iCount = NumberUtil.getRandomInt(1, 10);
		iCount = iCount > 2 ? 2 : iCount;
		//-------<2>.得到一个或两个随机的名
		String ming = "";
		String[] mingArray = null;
		for (int i = 0;i < iCount; i++) {
			ming = getRandomMing();
			mingArray = ming.split("\\|");
			hanzi += mingArray[0];
			pingying += mingArray[1];
		}
		return hanzi + "," + pingying;
	}
	
	/**
	 * 得到一个随机的姓，如: 司马|sima
	 * @return
	 */
	private String getRandomXing() {
		int i = NumberUtil.getRandomInt(0, this._xingSize - 1);
		return _xingArray[i];
	}
	
	/**
	 * 得到一个随机的名，如： 忠|zhong
	 * @return
	 */
	private String getRandomMing() {
		int i = NumberUtil.getRandomInt(0, this._mingSize - 1);
		String ming = _mingArray[i];
		String[] mingArray = ming.split("\\|");
		int j = NumberUtil.getRandomInt(0, mingArray[1].length() - 1);		
		return mingArray[1].substring(j, j + 1) + "|" + mingArray[0];
	}	
}
