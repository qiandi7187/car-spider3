package com.fxyz.chebao.pojo.carSpider;

import java.util.ArrayList;
import java.util.List;

public class CarTypeTempExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CarTypeTempExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andSeriesIdIsNull() {
            addCriterion("series_id is null");
            return (Criteria) this;
        }

        public Criteria andSeriesIdIsNotNull() {
            addCriterion("series_id is not null");
            return (Criteria) this;
        }

        public Criteria andSeriesIdEqualTo(Integer value) {
            addCriterion("series_id =", value, "seriesId");
            return (Criteria) this;
        }

        public Criteria andSeriesIdNotEqualTo(Integer value) {
            addCriterion("series_id <>", value, "seriesId");
            return (Criteria) this;
        }

        public Criteria andSeriesIdGreaterThan(Integer value) {
            addCriterion("series_id >", value, "seriesId");
            return (Criteria) this;
        }

        public Criteria andSeriesIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("series_id >=", value, "seriesId");
            return (Criteria) this;
        }

        public Criteria andSeriesIdLessThan(Integer value) {
            addCriterion("series_id <", value, "seriesId");
            return (Criteria) this;
        }

        public Criteria andSeriesIdLessThanOrEqualTo(Integer value) {
            addCriterion("series_id <=", value, "seriesId");
            return (Criteria) this;
        }

        public Criteria andSeriesIdIn(List<Integer> values) {
            addCriterion("series_id in", values, "seriesId");
            return (Criteria) this;
        }

        public Criteria andSeriesIdNotIn(List<Integer> values) {
            addCriterion("series_id not in", values, "seriesId");
            return (Criteria) this;
        }

        public Criteria andSeriesIdBetween(Integer value1, Integer value2) {
            addCriterion("series_id between", value1, value2, "seriesId");
            return (Criteria) this;
        }

        public Criteria andSeriesIdNotBetween(Integer value1, Integer value2) {
            addCriterion("series_id not between", value1, value2, "seriesId");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andGroupNameIsNull() {
            addCriterion("group_name is null");
            return (Criteria) this;
        }

        public Criteria andGroupNameIsNotNull() {
            addCriterion("group_name is not null");
            return (Criteria) this;
        }

        public Criteria andGroupNameEqualTo(String value) {
            addCriterion("group_name =", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameNotEqualTo(String value) {
            addCriterion("group_name <>", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameGreaterThan(String value) {
            addCriterion("group_name >", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameGreaterThanOrEqualTo(String value) {
            addCriterion("group_name >=", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameLessThan(String value) {
            addCriterion("group_name <", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameLessThanOrEqualTo(String value) {
            addCriterion("group_name <=", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameLike(String value) {
            addCriterion("group_name like", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameNotLike(String value) {
            addCriterion("group_name not like", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameIn(List<String> values) {
            addCriterion("group_name in", values, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameNotIn(List<String> values) {
            addCriterion("group_name not in", values, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameBetween(String value1, String value2) {
            addCriterion("group_name between", value1, value2, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameNotBetween(String value1, String value2) {
            addCriterion("group_name not between", value1, value2, "groupName");
            return (Criteria) this;
        }

        public Criteria andDrivingModeIsNull() {
            addCriterion("driving_mode is null");
            return (Criteria) this;
        }

        public Criteria andDrivingModeIsNotNull() {
            addCriterion("driving_mode is not null");
            return (Criteria) this;
        }

        public Criteria andDrivingModeEqualTo(String value) {
            addCriterion("driving_mode =", value, "drivingMode");
            return (Criteria) this;
        }

        public Criteria andDrivingModeNotEqualTo(String value) {
            addCriterion("driving_mode <>", value, "drivingMode");
            return (Criteria) this;
        }

        public Criteria andDrivingModeGreaterThan(String value) {
            addCriterion("driving_mode >", value, "drivingMode");
            return (Criteria) this;
        }

        public Criteria andDrivingModeGreaterThanOrEqualTo(String value) {
            addCriterion("driving_mode >=", value, "drivingMode");
            return (Criteria) this;
        }

        public Criteria andDrivingModeLessThan(String value) {
            addCriterion("driving_mode <", value, "drivingMode");
            return (Criteria) this;
        }

        public Criteria andDrivingModeLessThanOrEqualTo(String value) {
            addCriterion("driving_mode <=", value, "drivingMode");
            return (Criteria) this;
        }

        public Criteria andDrivingModeLike(String value) {
            addCriterion("driving_mode like", value, "drivingMode");
            return (Criteria) this;
        }

        public Criteria andDrivingModeNotLike(String value) {
            addCriterion("driving_mode not like", value, "drivingMode");
            return (Criteria) this;
        }

        public Criteria andDrivingModeIn(List<String> values) {
            addCriterion("driving_mode in", values, "drivingMode");
            return (Criteria) this;
        }

        public Criteria andDrivingModeNotIn(List<String> values) {
            addCriterion("driving_mode not in", values, "drivingMode");
            return (Criteria) this;
        }

        public Criteria andDrivingModeBetween(String value1, String value2) {
            addCriterion("driving_mode between", value1, value2, "drivingMode");
            return (Criteria) this;
        }

        public Criteria andDrivingModeNotBetween(String value1, String value2) {
            addCriterion("driving_mode not between", value1, value2, "drivingMode");
            return (Criteria) this;
        }

        public Criteria andTransmissionIsNull() {
            addCriterion("transmission is null");
            return (Criteria) this;
        }

        public Criteria andTransmissionIsNotNull() {
            addCriterion("transmission is not null");
            return (Criteria) this;
        }

        public Criteria andTransmissionEqualTo(String value) {
            addCriterion("transmission =", value, "transmission");
            return (Criteria) this;
        }

        public Criteria andTransmissionNotEqualTo(String value) {
            addCriterion("transmission <>", value, "transmission");
            return (Criteria) this;
        }

        public Criteria andTransmissionGreaterThan(String value) {
            addCriterion("transmission >", value, "transmission");
            return (Criteria) this;
        }

        public Criteria andTransmissionGreaterThanOrEqualTo(String value) {
            addCriterion("transmission >=", value, "transmission");
            return (Criteria) this;
        }

        public Criteria andTransmissionLessThan(String value) {
            addCriterion("transmission <", value, "transmission");
            return (Criteria) this;
        }

        public Criteria andTransmissionLessThanOrEqualTo(String value) {
            addCriterion("transmission <=", value, "transmission");
            return (Criteria) this;
        }

        public Criteria andTransmissionLike(String value) {
            addCriterion("transmission like", value, "transmission");
            return (Criteria) this;
        }

        public Criteria andTransmissionNotLike(String value) {
            addCriterion("transmission not like", value, "transmission");
            return (Criteria) this;
        }

        public Criteria andTransmissionIn(List<String> values) {
            addCriterion("transmission in", values, "transmission");
            return (Criteria) this;
        }

        public Criteria andTransmissionNotIn(List<String> values) {
            addCriterion("transmission not in", values, "transmission");
            return (Criteria) this;
        }

        public Criteria andTransmissionBetween(String value1, String value2) {
            addCriterion("transmission between", value1, value2, "transmission");
            return (Criteria) this;
        }

        public Criteria andTransmissionNotBetween(String value1, String value2) {
            addCriterion("transmission not between", value1, value2, "transmission");
            return (Criteria) this;
        }

        public Criteria andGuidePriceIsNull() {
            addCriterion("guide_price is null");
            return (Criteria) this;
        }

        public Criteria andGuidePriceIsNotNull() {
            addCriterion("guide_price is not null");
            return (Criteria) this;
        }

        public Criteria andGuidePriceEqualTo(String value) {
            addCriterion("guide_price =", value, "guidePrice");
            return (Criteria) this;
        }

        public Criteria andGuidePriceNotEqualTo(String value) {
            addCriterion("guide_price <>", value, "guidePrice");
            return (Criteria) this;
        }

        public Criteria andGuidePriceGreaterThan(String value) {
            addCriterion("guide_price >", value, "guidePrice");
            return (Criteria) this;
        }

        public Criteria andGuidePriceGreaterThanOrEqualTo(String value) {
            addCriterion("guide_price >=", value, "guidePrice");
            return (Criteria) this;
        }

        public Criteria andGuidePriceLessThan(String value) {
            addCriterion("guide_price <", value, "guidePrice");
            return (Criteria) this;
        }

        public Criteria andGuidePriceLessThanOrEqualTo(String value) {
            addCriterion("guide_price <=", value, "guidePrice");
            return (Criteria) this;
        }

        public Criteria andGuidePriceLike(String value) {
            addCriterion("guide_price like", value, "guidePrice");
            return (Criteria) this;
        }

        public Criteria andGuidePriceNotLike(String value) {
            addCriterion("guide_price not like", value, "guidePrice");
            return (Criteria) this;
        }

        public Criteria andGuidePriceIn(List<String> values) {
            addCriterion("guide_price in", values, "guidePrice");
            return (Criteria) this;
        }

        public Criteria andGuidePriceNotIn(List<String> values) {
            addCriterion("guide_price not in", values, "guidePrice");
            return (Criteria) this;
        }

        public Criteria andGuidePriceBetween(String value1, String value2) {
            addCriterion("guide_price between", value1, value2, "guidePrice");
            return (Criteria) this;
        }

        public Criteria andGuidePriceNotBetween(String value1, String value2) {
            addCriterion("guide_price not between", value1, value2, "guidePrice");
            return (Criteria) this;
        }

        public Criteria andDealerPriceIsNull() {
            addCriterion("dealer_price is null");
            return (Criteria) this;
        }

        public Criteria andDealerPriceIsNotNull() {
            addCriterion("dealer_price is not null");
            return (Criteria) this;
        }

        public Criteria andDealerPriceEqualTo(String value) {
            addCriterion("dealer_price =", value, "dealerPrice");
            return (Criteria) this;
        }

        public Criteria andDealerPriceNotEqualTo(String value) {
            addCriterion("dealer_price <>", value, "dealerPrice");
            return (Criteria) this;
        }

        public Criteria andDealerPriceGreaterThan(String value) {
            addCriterion("dealer_price >", value, "dealerPrice");
            return (Criteria) this;
        }

        public Criteria andDealerPriceGreaterThanOrEqualTo(String value) {
            addCriterion("dealer_price >=", value, "dealerPrice");
            return (Criteria) this;
        }

        public Criteria andDealerPriceLessThan(String value) {
            addCriterion("dealer_price <", value, "dealerPrice");
            return (Criteria) this;
        }

        public Criteria andDealerPriceLessThanOrEqualTo(String value) {
            addCriterion("dealer_price <=", value, "dealerPrice");
            return (Criteria) this;
        }

        public Criteria andDealerPriceLike(String value) {
            addCriterion("dealer_price like", value, "dealerPrice");
            return (Criteria) this;
        }

        public Criteria andDealerPriceNotLike(String value) {
            addCriterion("dealer_price not like", value, "dealerPrice");
            return (Criteria) this;
        }

        public Criteria andDealerPriceIn(List<String> values) {
            addCriterion("dealer_price in", values, "dealerPrice");
            return (Criteria) this;
        }

        public Criteria andDealerPriceNotIn(List<String> values) {
            addCriterion("dealer_price not in", values, "dealerPrice");
            return (Criteria) this;
        }

        public Criteria andDealerPriceBetween(String value1, String value2) {
            addCriterion("dealer_price between", value1, value2, "dealerPrice");
            return (Criteria) this;
        }

        public Criteria andDealerPriceNotBetween(String value1, String value2) {
            addCriterion("dealer_price not between", value1, value2, "dealerPrice");
            return (Criteria) this;
        }

        public Criteria andSecondPriceIsNull() {
            addCriterion("second_price is null");
            return (Criteria) this;
        }

        public Criteria andSecondPriceIsNotNull() {
            addCriterion("second_price is not null");
            return (Criteria) this;
        }

        public Criteria andSecondPriceEqualTo(String value) {
            addCriterion("second_price =", value, "secondPrice");
            return (Criteria) this;
        }

        public Criteria andSecondPriceNotEqualTo(String value) {
            addCriterion("second_price <>", value, "secondPrice");
            return (Criteria) this;
        }

        public Criteria andSecondPriceGreaterThan(String value) {
            addCriterion("second_price >", value, "secondPrice");
            return (Criteria) this;
        }

        public Criteria andSecondPriceGreaterThanOrEqualTo(String value) {
            addCriterion("second_price >=", value, "secondPrice");
            return (Criteria) this;
        }

        public Criteria andSecondPriceLessThan(String value) {
            addCriterion("second_price <", value, "secondPrice");
            return (Criteria) this;
        }

        public Criteria andSecondPriceLessThanOrEqualTo(String value) {
            addCriterion("second_price <=", value, "secondPrice");
            return (Criteria) this;
        }

        public Criteria andSecondPriceLike(String value) {
            addCriterion("second_price like", value, "secondPrice");
            return (Criteria) this;
        }

        public Criteria andSecondPriceNotLike(String value) {
            addCriterion("second_price not like", value, "secondPrice");
            return (Criteria) this;
        }

        public Criteria andSecondPriceIn(List<String> values) {
            addCriterion("second_price in", values, "secondPrice");
            return (Criteria) this;
        }

        public Criteria andSecondPriceNotIn(List<String> values) {
            addCriterion("second_price not in", values, "secondPrice");
            return (Criteria) this;
        }

        public Criteria andSecondPriceBetween(String value1, String value2) {
            addCriterion("second_price between", value1, value2, "secondPrice");
            return (Criteria) this;
        }

        public Criteria andSecondPriceNotBetween(String value1, String value2) {
            addCriterion("second_price not between", value1, value2, "secondPrice");
            return (Criteria) this;
        }

        public Criteria andTaxIsNull() {
            addCriterion("tax is null");
            return (Criteria) this;
        }

        public Criteria andTaxIsNotNull() {
            addCriterion("tax is not null");
            return (Criteria) this;
        }

        public Criteria andTaxEqualTo(String value) {
            addCriterion("tax =", value, "tax");
            return (Criteria) this;
        }

        public Criteria andTaxNotEqualTo(String value) {
            addCriterion("tax <>", value, "tax");
            return (Criteria) this;
        }

        public Criteria andTaxGreaterThan(String value) {
            addCriterion("tax >", value, "tax");
            return (Criteria) this;
        }

        public Criteria andTaxGreaterThanOrEqualTo(String value) {
            addCriterion("tax >=", value, "tax");
            return (Criteria) this;
        }

        public Criteria andTaxLessThan(String value) {
            addCriterion("tax <", value, "tax");
            return (Criteria) this;
        }

        public Criteria andTaxLessThanOrEqualTo(String value) {
            addCriterion("tax <=", value, "tax");
            return (Criteria) this;
        }

        public Criteria andTaxLike(String value) {
            addCriterion("tax like", value, "tax");
            return (Criteria) this;
        }

        public Criteria andTaxNotLike(String value) {
            addCriterion("tax not like", value, "tax");
            return (Criteria) this;
        }

        public Criteria andTaxIn(List<String> values) {
            addCriterion("tax in", values, "tax");
            return (Criteria) this;
        }

        public Criteria andTaxNotIn(List<String> values) {
            addCriterion("tax not in", values, "tax");
            return (Criteria) this;
        }

        public Criteria andTaxBetween(String value1, String value2) {
            addCriterion("tax between", value1, value2, "tax");
            return (Criteria) this;
        }

        public Criteria andTaxNotBetween(String value1, String value2) {
            addCriterion("tax not between", value1, value2, "tax");
            return (Criteria) this;
        }

        public Criteria andStateIsNull() {
            addCriterion("state is null");
            return (Criteria) this;
        }

        public Criteria andStateIsNotNull() {
            addCriterion("state is not null");
            return (Criteria) this;
        }

        public Criteria andStateEqualTo(String value) {
            addCriterion("state =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(String value) {
            addCriterion("state <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(String value) {
            addCriterion("state >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(String value) {
            addCriterion("state >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(String value) {
            addCriterion("state <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(String value) {
            addCriterion("state <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLike(String value) {
            addCriterion("state like", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotLike(String value) {
            addCriterion("state not like", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<String> values) {
            addCriterion("state in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<String> values) {
            addCriterion("state not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(String value1, String value2) {
            addCriterion("state between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(String value1, String value2) {
            addCriterion("state not between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andOrlIsNull() {
            addCriterion("orl is null");
            return (Criteria) this;
        }

        public Criteria andOrlIsNotNull() {
            addCriterion("orl is not null");
            return (Criteria) this;
        }

        public Criteria andOrlEqualTo(Integer value) {
            addCriterion("orl =", value, "orl");
            return (Criteria) this;
        }

        public Criteria andOrlNotEqualTo(Integer value) {
            addCriterion("orl <>", value, "orl");
            return (Criteria) this;
        }

        public Criteria andOrlGreaterThan(Integer value) {
            addCriterion("orl >", value, "orl");
            return (Criteria) this;
        }

        public Criteria andOrlGreaterThanOrEqualTo(Integer value) {
            addCriterion("orl >=", value, "orl");
            return (Criteria) this;
        }

        public Criteria andOrlLessThan(Integer value) {
            addCriterion("orl <", value, "orl");
            return (Criteria) this;
        }

        public Criteria andOrlLessThanOrEqualTo(Integer value) {
            addCriterion("orl <=", value, "orl");
            return (Criteria) this;
        }

        public Criteria andOrlIn(List<Integer> values) {
            addCriterion("orl in", values, "orl");
            return (Criteria) this;
        }

        public Criteria andOrlNotIn(List<Integer> values) {
            addCriterion("orl not in", values, "orl");
            return (Criteria) this;
        }

        public Criteria andOrlBetween(Integer value1, Integer value2) {
            addCriterion("orl between", value1, value2, "orl");
            return (Criteria) this;
        }

        public Criteria andOrlNotBetween(Integer value1, Integer value2) {
            addCriterion("orl not between", value1, value2, "orl");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}