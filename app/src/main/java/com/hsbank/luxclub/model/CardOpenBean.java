package com.hsbank.luxclub.model;

import java.util.List;

/**
 * 推荐开卡
 * Created by chenliuchun on 17/5/19.
 */

public class CardOpenBean {

    private List<CardRuleListBean> cardRuleList;
    private List<StewardListBean> stewardList;
    private List<InterestListBean> interestList;

    public List<CardRuleListBean> getCardRuleList() {
        return cardRuleList;
    }

    public void setCardRuleList(List<CardRuleListBean> cardRuleList) {
        this.cardRuleList = cardRuleList;
    }

    public List<StewardListBean> getStewardList() {
        return stewardList;
    }

    public void setStewardList(List<StewardListBean> stewardList) {
        this.stewardList = stewardList;
    }

    public List<InterestListBean> getInterestList() {
        return interestList;
    }

    public void setInterestList(List<InterestListBean> interestList) {
        this.interestList = interestList;
    }

    public static class CardRuleListBean {
        /**
         * ruleId : 5
         * ruleName : 0元卡
         * amount : 0.00
         * actualAmount : 0.00
         */

        private int ruleId;
        private String ruleName;
        private String amount;
        private String actualAmount;

        public int getRuleId() {
            return ruleId;
        }

        public void setRuleId(int ruleId) {
            this.ruleId = ruleId;
        }

        public String getRuleName() {
            return ruleName;
        }

        public void setRuleName(String ruleName) {
            this.ruleName = ruleName;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getActualAmount() {
            return actualAmount;
        }

        public void setActualAmount(String actualAmount) {
            this.actualAmount = actualAmount;
        }
    }

    public static class StewardListBean {
        /**
         * stewardId : 31
         * stewardName : 管家1号
         */

        private String stewardId;
        private String stewardName;

        public String getStewardId() {
            return stewardId;
        }

        public void setStewardId(String stewardId) {
            this.stewardId = stewardId;
        }

        public String getStewardName() {
            return stewardName;
        }

        public void setStewardName(String stewardName) {
            this.stewardName = stewardName;
        }
    }

    public static class InterestListBean {
        /**
         * interestId : 1
         * interestName : 私人游艇
         */

        private String interestId;
        private String interestName;

        public String getInterestId() {
            return interestId;
        }

        public void setInterestId(String interestId) {
            this.interestId = interestId;
        }

        public String getInterestName() {
            return interestName;
        }

        public void setInterestName(String interestName) {
            this.interestName = interestName;
        }
    }
}
