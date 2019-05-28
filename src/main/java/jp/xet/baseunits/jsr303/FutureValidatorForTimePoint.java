/*
 * Copyright 2010-2019 Miyamoto Daisuke.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.xet.baseunits.jsr303;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Future;

import jp.xet.baseunits.time.TimePoint;
import jp.xet.baseunits.timeutil.Clock;

/**
 * {@link TimePoint}値の{@link Future}アノテーションによるバリデータ実装クラス。
 * 
 * @author daisuke
 * @since 2.9
 */
public class FutureValidatorForTimePoint implements ConstraintValidator<Future, TimePoint> {
	
	@Override
	public void initialize(Future constraintAnnotation) {
	}
	
	@Override
	public boolean isValid(TimePoint value, ConstraintValidatorContext context) {
		// null values are valid
		if (value == null) {
			return true;
		}
		return value.isAfter(Clock.now());
	}
}
