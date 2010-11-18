/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com)
 * This free software is distributed under the "MIT" licence. See file licence.txt. 
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package com.domainlanguage.intervals;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The rules of this class are consistent with the common mathematical
 * definition of "interval". For a simple explanation, see
 * http://en.wikipedia.org/wiki/Interval_(mathematics)
 * 
 * Interval (and its "ConcreteInterval" subclass) can be used for any objects
 * that have a natural ordering reflected by implementing the Comparable
 * interface. For example, Integer implements Comparable, so if you want to
 * check if an Integer is within a range, make an Interval. Any class of yours
 * which implements Comparable can have intervals defined this way.
 */
public class Interval implements Comparable, Serializable {
	
	/** TODO for daisuke */
	private static final long serialVersionUID = 2039990061977191418L;
	

	public static Interval closed(Comparable lower, Comparable upper) {
		return new Interval(lower, true, upper, true);
	}
	
	public static Interval open(Comparable lower, Comparable upper) {
		return new Interval(lower, false, upper, false);
	}
	
	public static Interval over(Comparable lower, boolean lowerIncluded, Comparable upper, boolean upperIncluded) {
		return new Interval(lower, lowerIncluded, upper, upperIncluded);
	}
	

	private IntervalLimit lowerLimitObject;
	
	private IntervalLimit upperLimitObject;
	

	//Only for use by persistence mapping frameworks
	//<rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	protected Interval() {
	}
	
	protected Interval(Comparable lower, boolean isLowerClosed, Comparable upper, boolean isUpperClosed) {
		this(IntervalLimit.lower(isLowerClosed, lower), IntervalLimit.upper(isUpperClosed, upper));
	}
	
	Interval(IntervalLimit lower, IntervalLimit upper) {
		assertLowerIsLessThanOrEqualUpper(lower, upper);
		this.lowerLimitObject = lower;
		this.upperLimitObject = upper;
	}
	
	//Warning: This method should generally be used for display
	//purposes and interactions with closely coupled classes.
	//Look for (or add) other methods to do computations.
	
	@Override
	public int compareTo(Object arg) {
		Interval other = (Interval) arg;
		if (!upperLimit().equals(other.upperLimit())) {
			return upperLimit().compareTo(other.upperLimit());
		}
		if (includesLowerLimit() && !other.includesLowerLimit()) {
			return -1;
		}
		if (!includesLowerLimit() && other.includesLowerLimit()) {
			return 1;
		}
		return lowerLimit().compareTo(other.lowerLimit());
	}
	
	//Warning: This method should generally be used for display
	//purposes and interactions with closely coupled classes.
	//Look for (or add) other methods to do computations.
	
	/** see: http://en.wikipedia.org/wiki/Set_theoretic_complement */
	public List complementRelativeTo(Interval other) {
		List intervalSequence = new ArrayList();
		if (!this.intersects(other)) {
			intervalSequence.add(other);
			return intervalSequence;
		}
		Interval left = leftComplementRelativeTo(other);
		if (left != null) {
			intervalSequence.add(left);
		}
		Interval right = rightComplementRelativeTo(other);
		if (right != null) {
			intervalSequence.add(right);
		}
		return intervalSequence;
	}
	
	public boolean covers(Interval other) {
		int lowerComparison = lowerLimit().compareTo(other.lowerLimit());
		boolean lowerPass = this.includes(other.lowerLimit()) || (lowerComparison == 0 && !other.includesLowerLimit());
		int upperComparison = upperLimit().compareTo(other.upperLimit());
		boolean upperPass = this.includes(other.upperLimit()) || (upperComparison == 0 && !other.includesUpperLimit());
		return lowerPass && upperPass;
	}
	
	public Interval emptyOfSameType() {
		return newOfSameType(lowerLimit(), false, lowerLimit(), false);
	}
	
	public boolean equals(Interval other) {
		if (other == null) {
			return false;
		}
		
		boolean thisEmpty = this.isEmpty();
		boolean otherEmpty = other.isEmpty();
		if (thisEmpty & otherEmpty) {
			return true;
		}
		if (thisEmpty ^ otherEmpty) {
			return false;
		}
		
		boolean thisSingle = this.isSingleElement();
		boolean otherSingle = other.isSingleElement();
		if (thisSingle & otherSingle) {
			return this.lowerLimit().equals(other.lowerLimit());
		}
		if (thisSingle ^ otherSingle) {
			return false;
		}
		
		return compareTo(other) == 0;
	}
	
	@Override
	public boolean equals(Object other) {
		try {
			return equals((Interval) other);
		} catch (ClassCastException ex) {
			return false;
		}
	}
	
	public Interval gap(Interval other) {
		if (this.intersects(other)) {
			return this.emptyOfSameType();
		}
		
		return newOfSameType(lesserOfUpperLimits(other), !lesserOfUpperIncludedInUnion(other),
				greaterOfLowerLimits(other), !greaterOfLowerIncludedInUnion(other));
	}
	
	@Override
	public int hashCode() {
		return lowerLimit().hashCode() ^ upperLimit().hashCode();
	}
	
	//Warning: This method should generally be used for display
	//purposes and interactions with closely coupled classes.
	//Look for (or add) other methods to do computations.
	public boolean hasLowerLimit() {
		return lowerLimit() != null;
	}
	
	public boolean hasUpperLimit() {
		return upperLimit() != null;
	}
	
	public boolean includes(Comparable value) {
		return !this.isBelow(value) && !this.isAbove(value);
	}
	
	//Warning: This method should generally be used for display
	//purposes and interactions with closely coupled classes.
	//Look for (or add) other methods to do computations.
	public boolean includesLowerLimit() {
		return lowerLimitObject.isClosed();
	}
	
	public boolean includesUpperLimit() {
		return upperLimitObject.isClosed();
	}
	
	public Interval intersect(Interval other) {
		Comparable intersectLowerBound = greaterOfLowerLimits(other);
		Comparable intersectUpperBound = lesserOfUpperLimits(other);
		if (intersectLowerBound.compareTo(intersectUpperBound) > 0) {
			return emptyOfSameType();
		}
		return newOfSameType(intersectLowerBound, greaterOfLowerIncludedInIntersection(other), intersectUpperBound,
				lesserOfUpperIncludedInIntersection(other));
	}
	
	public boolean intersects(Interval other) {
		int comparison = greaterOfLowerLimits(other).compareTo(lesserOfUpperLimits(other));
		if (comparison < 0) {
			return true;
		}
		if (comparison > 0) {
			return false;
		}
		return greaterOfLowerIncludedInIntersection(other) && lesserOfUpperIncludedInIntersection(other);
	}
	
	public boolean isAbove(Comparable value) {
		if (!hasLowerLimit()) {
			return false;
		}
		int comparison = lowerLimit().compareTo(value);
		return comparison > 0 || (comparison == 0 && !includesLowerLimit());
	}
	
	public boolean isBelow(Comparable value) {
		if (!hasUpperLimit()) {
			return false;
		}
		int comparison = upperLimit().compareTo(value);
		return comparison < 0 || (comparison == 0 && !includesUpperLimit());
	}
	
	public boolean isClosed() {
		return includesLowerLimit() && includesUpperLimit();
	}
	
	public boolean isEmpty() {
		//TODO: Consider explicit empty interval
		//A 'degenerate' interval is an empty set, {}.
		return isOpen() && upperLimit().equals(lowerLimit());
	}
	
	public boolean isOpen() {
		return !includesLowerLimit() && !includesUpperLimit();
	}
	
	public boolean isSingleElement() {
		if (!hasUpperLimit()) {
			return false;
		}
		if (!hasLowerLimit()) {
			return false;
		}
		//An interval containing a single element, {a}.
		return upperLimit().equals(lowerLimit()) && !isEmpty();
	}
	
	//Warning: This method should generally be used for display
	//purposes and interactions with closely coupled classes.
	//Look for (or add) other methods to do computations.
	public Comparable lowerLimit() {
		return lowerLimitObject.getValue();
	}
	
	public Interval newOfSameType(Comparable lower, boolean isLowerClosed, Comparable upper, boolean isUpperClosed) {
		return new Interval(lower, isLowerClosed, upper, isUpperClosed);
	}
	
	@Override
	public String toString() {
		if (isEmpty()) {
			return "{}";
		}
		if (isSingleElement()) {
			return "{" + lowerLimit().toString() + "}";
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append(includesLowerLimit() ? "[" : "(");
		buffer.append(hasLowerLimit() ? lowerLimit().toString() : "Infinity");
		buffer.append(", ");
		buffer.append(hasUpperLimit() ? upperLimit().toString() : "Infinity");
		buffer.append(includesUpperLimit() ? "]" : ")");
		return buffer.toString();
	}
	
	//Warning: This method should generally be used for display
	//purposes and interactions with closely coupled classes.
	//Look for (or add) other methods to do computations.
	public Comparable upperLimit() {
		return upperLimitObject.getValue();
	}
	
	Comparable greaterOfLowerLimits(Interval other) {
		if (lowerLimit() == null) {
			return other.lowerLimit();
		}
		int lowerComparison = lowerLimit().compareTo(other.lowerLimit());
		if (lowerComparison >= 0) {
			return this.lowerLimit();
		}
		return other.lowerLimit();
	}
	
	Comparable lesserOfUpperLimits(Interval other) {
		if (upperLimit() == null) {
			return other.upperLimit();
		}
		int upperComparison = upperLimit().compareTo(other.upperLimit());
		if (upperComparison <= 0) {
			return this.upperLimit();
		}
		return other.upperLimit();
	}
	
	private void assertLowerIsLessThanOrEqualUpper(IntervalLimit lower, IntervalLimit upper) {
		if (!(lower.isLower() && upper.isUpper() && lower.compareTo(upper) <= 0)) {
			throw new IllegalArgumentException(lower + " is not before or equal to " + upper);
		}
	}
	
	//Only for use by persistence mapping frameworks
	//<rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	private Comparable getForPersistentMapping_LowerLimit() {
		return lowerLimitObject.getForPersistentMapping_Value();
	}
	
	//Only for use by persistence mapping frameworks
	//<rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	private Comparable getForPersistentMapping_UpperLimit() {
		return upperLimitObject.getForPersistentMapping_Value();
	}
	
	private boolean greaterOfLowerIncludedInIntersection(Interval other) {
		Comparable limit = greaterOfLowerLimits(other);
		return this.includes(limit) && other.includes(limit);
	}
	
	private boolean greaterOfLowerIncludedInUnion(Interval other) {
		Comparable limit = greaterOfLowerLimits(other);
		return this.includes(limit) || other.includes(limit);
	}
	
	private Comparable greaterOfUpperLimits(Interval other) {
		if (upperLimit() == null) {
			return null;
		}
		int upperComparison = upperLimit().compareTo(other.upperLimit());
		if (upperComparison >= 0) {
			return this.upperLimit();
		}
		return other.upperLimit();
	}
	
	//Only for use by persistence mapping frameworks
	//<rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	private boolean isForPersistentMapping_IncludesLowerLimit() {
		return !lowerLimitObject.isForPersistentMapping_Closed();
	}
	
	//Only for use by persistence mapping frameworks
	//<rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	private boolean isForPersistentMapping_IncludesUpperLimit() {
		return !upperLimitObject.isForPersistentMapping_Closed();
	}
	
	private Interval leftComplementRelativeTo(Interval other) {
		if (this.includes(lesserOfLowerLimits(other))) {
			return null;
		}
		if (lowerLimit().equals(other.lowerLimit()) && !other.includesLowerLimit()) {
			return null;
		}
		return newOfSameType(other.lowerLimit(), other.includesLowerLimit(), this.lowerLimit(),
				!this.includesLowerLimit());
	}
	
	private Comparable lesserOfLowerLimits(Interval other) {
		if (lowerLimit() == null) {
			return null;
		}
		int lowerComparison = lowerLimit().compareTo(other.lowerLimit());
		if (lowerComparison <= 0) {
			return this.lowerLimit();
		}
		return other.lowerLimit();
	}
	
	private boolean lesserOfUpperIncludedInIntersection(Interval other) {
		Comparable limit = lesserOfUpperLimits(other);
		return this.includes(limit) && other.includes(limit);
	}
	
	private boolean lesserOfUpperIncludedInUnion(Interval other) {
		Comparable limit = lesserOfUpperLimits(other);
		return this.includes(limit) || other.includes(limit);
	}
	
	private Interval rightComplementRelativeTo(Interval other) {
		if (this.includes(greaterOfUpperLimits(other))) {
			return null;
		}
		if (upperLimit().equals(other.upperLimit()) && !other.includesUpperLimit()) {
			return null;
		}
		return newOfSameType(this.upperLimit(), !this.includesUpperLimit(), other.upperLimit(),
				other.includesUpperLimit());
	}
	
	//Only for use by persistence mapping frameworks
	//<rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	private void setForPersistentMapping_IncludesLowerLimit(boolean value) {
		if (lowerLimitObject == null) {
			lowerLimitObject = IntervalLimit.lower(value, null);
		}
		lowerLimitObject.setForPersistentMapping_Closed(!value);
	}
	
	//Only for use by persistence mapping frameworks
	//<rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	private void setForPersistentMapping_IncludesUpperLimit(boolean value) {
		if (upperLimitObject == null) {
			upperLimitObject = IntervalLimit.upper(value, null);
		}
		upperLimitObject.setForPersistentMapping_Closed(!value);
	}
	
	//Only for use by persistence mapping frameworks
	//<rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	private void setForPersistentMapping_LowerLimit(Comparable value) {
		if (lowerLimitObject == null) {
			lowerLimitObject = IntervalLimit.lower(true, value);
		}
		lowerLimitObject.setForPersistentMapping_Value(value);
	}
	
	//Only for use by persistence mapping frameworks
	//<rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	private void setForPersistentMapping_UpperLimit(Comparable value) {
		if (upperLimitObject == null) {
			upperLimitObject = IntervalLimit.upper(true, value);
		}
		upperLimitObject.setForPersistentMapping_Value(value);
	}
}
