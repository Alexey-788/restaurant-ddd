package com.alex788.restaurant.shop.rest.endpoint.add_meal_to_menu;

import com.alex788.restaurant.shop.domain.menu.value_object.MealDescription;
import com.alex788.restaurant.shop.domain.menu.value_object.MealName;
import com.alex788.restaurant.shop.domain.menu.value_object.MealPrice;
import com.alex788.restaurant.shop.domain.menu.value_object.error.MealDescriptionError;
import com.alex788.restaurant.shop.domain.menu.value_object.error.MealNameError;
import com.alex788.restaurant.shop.domain.menu.value_object.error.MealPriceError;
import com.alex788.restaurant.shop.rest.model.ErrorMessage;
import com.alex788.restaurant.shop.usecase.AddMealToMenu;
import io.vavr.control.Either;

public class AddMealToMenuRequestValidator {

    public Either<ErrorMessage, AddMealToMenu.AddMealToMenuRequest> validate(AddMealToMenuEndpoint.Request request) {
        Either<MealNameError, MealName> mealNameEth = MealName.from(request.getMealModel().getName());
        Either<MealDescriptionError, MealDescription> mealDescriptionEth = MealDescription.from(request.getMealModel().getDescription());
        Either<MealPriceError, MealPrice> mealPriceEth = MealPrice.from(request.getMealModel().getPrice());

        ErrorBuilder errorBuilder = new ErrorBuilder();

        if (mealNameEth.isLeft()) {
            errorBuilder.append(mealNameEth.getLeft());
        }
        if (mealDescriptionEth.isLeft()) {
            errorBuilder.append(mealDescriptionEth.getLeft());
        }
        if (mealPriceEth.isLeft()) {
            errorBuilder.append(mealPriceEth.getLeft());
        }

        if (errorBuilder.notEmpty()) {
            return Either.left(new ErrorMessage(errorBuilder.finish()));
        }

        return Either.right(new AddMealToMenu.AddMealToMenuRequest(mealNameEth.get(), mealDescriptionEth.get(), mealPriceEth.get()));
    }

    private static class ErrorBuilder {
        private final static String ERROR_PREFIX = "Invalid parameters: ";
        private final static String ERROR_POSTFIX = ".";
        private final static String ERROR_SEPARATOR = ", ";

        private final static MealNameError.ErrorMessageMapper mealNameErrorMessageMapper = new MealNameErrorMessageMapper();
        private final static MealDescriptionError.ErrorMessageMapper mealDescriptionErrorMessageMapper = new MealDescriptionErrorMessageMapper();
        private final static MealPriceError.ErrorMessageMapper mealPriceErrorMessageMapper = new MealPriceErrorMessageMapper();

        private enum Status {
            STARTED, IN_PROCESS, FINISHED
        }

        private Status status = Status.STARTED;
        private StringBuilder messageBuilder;

        public void append(MealNameError error) {
            appendPrepare();
            messageBuilder.append(error.accept(mealNameErrorMessageMapper))
                    .append(ERROR_SEPARATOR);
        }

        public void append(MealDescriptionError error) {
            appendPrepare();
            messageBuilder.append(error.accept(mealDescriptionErrorMessageMapper))
                    .append(ERROR_SEPARATOR);
        }

        public void append(MealPriceError error) {
            appendPrepare();
            messageBuilder.append(error.accept(mealPriceErrorMessageMapper))
                    .append(ERROR_SEPARATOR);
        }

        private void appendPrepare() {
            if (status == Status.FINISHED) throw new IllegalStateException("Cannot append: finished status.");
            if (status == Status.STARTED) {
                status = Status.IN_PROCESS;
                messageBuilder = new StringBuilder(ERROR_PREFIX);
            }
        }

        public String finish() {
            finishPrepare();
            return messageBuilder.toString();
        }

        private void finishPrepare() {
            if (status == Status.STARTED) throw new IllegalStateException("Cannot finish: no error appended.");
            if (status == Status.IN_PROCESS) {
                messageBuilder.delete(messageBuilder.length() - ERROR_SEPARATOR.length(), messageBuilder.length());
                messageBuilder.append(ERROR_POSTFIX);
            }
        }

        public boolean notEmpty() {
            return status != Status.STARTED;
        }
    }

    private static class MealNameErrorMessageMapper implements MealNameError.ErrorMessageMapper {

        @Override
        public String visit(MealNameError.BlankError error) {
            return "name must be not blank";
        }
    }

    private static class MealDescriptionErrorMessageMapper implements MealDescriptionError.ErrorMessageMapper {

        @Override
        public String visit(MealDescriptionError.BlankError error) {
            return "description must be not blank";
        }
    }

    private static class MealPriceErrorMessageMapper implements MealPriceError.ErrorMessageMapper {

        @Override
        public String visit(MealPriceError.WrongScaleError error) {
            return "wrong price scale";
        }

        @Override
        public String visit(MealPriceError.NegativeError error) {
            return "price must be positive";
        }
    }
}
