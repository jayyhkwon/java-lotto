package step3.lotto.client;

import step3.lotto.Lotto;
import step3.lotto.Lottos;
import step3.lotto.TotalLottoNumber;
import step3.lotto.exception.NotEnoughMoneyException;
import step3.lotto.result.LottoResult;
import step3.lotto.result.LottoResults;
import step3.lotto.result.ResultStatistic;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class Client {

    private Money money;
    private Lottos lottos;

    public Client(long money) {
        this.money = new Money(money);
    }

    public void buyLotto(Lottos lottos) {
        int lottoPrice = lottos.price();
        this.lottos = lottos;
        this.money.minus(lottoPrice);
    }

    public long buyableLottoCount() {
        return money.getValue() / Lotto.PRICE;
    }

    public long remainMoney() {
        return this.money.getValue();
    }

    public ResultStatistic calculateResultStatistic(TotalLottoNumber totalLottoNumber) {
        int boughtLottoCount = lottos.count();
        LottoResults lottoResults = checkResultOfLotto(totalLottoNumber);
        long sumOfPrizeMoney = lottoResults.sumOfPrizeMoney();
        double totalRevenueRate = getTotalRevenueRate(boughtLottoCount, sumOfPrizeMoney);
        return new ResultStatistic(totalRevenueRate, lottoResults);
    }

    private double getTotalRevenueRate(long boughtLottoCount, long sumOfPrizeMoney) {
        BigDecimal totalPrizeMoney = BigDecimal.valueOf(sumOfPrizeMoney);
        BigDecimal lottoBoughtAmount = BigDecimal.valueOf(boughtLottoCount * Lotto.PRICE);

        double totalRevenueRate = totalPrizeMoney.divide(lottoBoughtAmount, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return totalRevenueRate;
    }

    private LottoResults checkResultOfLotto(TotalLottoNumber winningNumber) {
        List<LottoResult> lottoResults = lottos.stream()
                .map(lotto -> new LottoResult(winningNumber, lotto))
                .collect(Collectors.toList());

        return new LottoResults(lottoResults);
    }

}
