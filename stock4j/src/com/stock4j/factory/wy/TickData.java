package com.stock4j.factory.wy;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock4j.ExRightType;
import com.stock4j.PeriodType;
import com.stock4j.Stock;
import com.stock4j.Tick;
import com.stock4j.exception.ErrorHttpException;
import com.stock4j.exception.NullValueException;
import com.stock4j.exception.UnSupportedException;
import com.stock4j.factory.HttpClientPool;

class TickData extends HttpClientPool {
	
	private static ObjectMapper mapper = new ObjectMapper();
	private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

	/**
	 * ��ȡ��Ʊ���������ݣ���ȡ���ݵ����ݿ��Ժܴ�
	 * http://img1.money.126.net/data/hs/kline/day/history/2017/0600510.json
	 * http://img1.money.126.net/data/hs/klinederc/day/history/2017/0600510.json
	 * ��Ȩ
	 * http://img1.money.126.net/data/hs/klinederc/week/history/2017/0600510.json
	 * ���� ��Ȩ
	 * http://img1.money.126.net/data/hs/klinederc/month/history/2017/0600510.json
	 * ���� ���ݰ�ʱ������
	 */
	protected List<Tick> listTicks(Stock stock, PeriodType period, int size, ExRightType rhb)
			throws ErrorHttpException, NullValueException, UnSupportedException {
		if (period.name().startsWith("MIN"))
			throw new UnSupportedException("��֧�����߼������µ�K��");

		String scode = stock.getScode();
		String rhbType = "kline";
		String ptype = "day";
		// K������
		switch (period) {
		case DAY:
			break;
		case WEEK:
			ptype = "week";
			break;
		case MONTH:
			ptype = "month";
			break;
		case YEAR:
			ptype = "year";
			break;
		default:
			ptype = "day";
			break;
		}

		// ֤ȯ����
		switch (stock.getMarket()) {
		case SH:
			scode = "0" + scode;
			break;
		case SZ:
			scode = "1" + scode;
			break;
		default:
			throw new UnSupportedException("��֧�ֵ����ݲ���");
		}

		// ��Ȩ��ʽ
		switch (rhb) {
		case FORWARD:
			throw new UnSupportedException("��֧��ǰ��Ȩ�ķ�ʽ");
		case NO:
			rhbType = "kline";
			break;
		case BACKWARD:
			rhbType = "klinederc";
			break;
		default:
			break;
		}

		LocalDate date = LocalDate.now();
		int realLen = 0;
		List<Tick> ticks = new ArrayList<Tick>();
		// ��ȡ����
		do {
			String url = "http://img1.money.126.net/data/hs/" + rhbType + "/" + ptype + "/history/" + date.getYear()
					+ "/" + scode + ".json";
			try {
				String result = super.get(url, null, "utf-8");
				Tick tick;
				try {
					JsonNode node = mapper.readTree(result);
					JsonNode data = node.get("data");
					JsonNode temp;
					stock.setSname(node.path("name").asText());
					int len = data.size();
					for(int i = 0; i < len; i++){
						temp = data.get(i);
						tick = new Tick(period);
						tick.setEndDateTime(LocalDateTime.parse(temp.get(0).asText(), dateFormatter));
						tick.setOpen(temp.get(1).asDouble());
						tick.setClose(temp.get(2).asDouble());
						tick.setHigh(temp.get(3).asDouble());
						tick.setLow(temp.get(4).asDouble());
						tick.setTradingVolume(temp.get(5).asLong() * 100L);

						ticks.add(tick);
					}
					realLen += len;
					date = date.minusYears(1);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} catch (ErrorHttpException e) {
				break;  //�޷��ٻ�ȡ����ʱ������ѭ��
			}
		} while (realLen < size);

		Collections.sort(ticks, new Comparator<Tick>() {
			@Override
			public int compare(Tick o1, Tick o2) {
				return o1.compareTo(o2);
			}
		});

		if(size >= realLen) return ticks;
		else return ticks.subList(realLen - size, realLen);
	}

}
