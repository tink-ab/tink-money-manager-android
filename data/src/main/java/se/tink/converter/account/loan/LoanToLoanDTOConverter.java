package se.tink.converter.account.loan;

import se.tink.converter.ModelConverter;
import se.tink.converter.ModelConverterImpl;
import se.tink.core.models.account.Loan;
import se.tink.core.models.misc.ExactNumber;
import se.tink.modelConverter.AbstractConverter;

public class LoanToLoanDTOConverter extends
        AbstractConverter<se.tink.grpc.v1.models.Loan, Loan> {

    private ModelConverter modelConverter;

    public LoanToLoanDTOConverter(ModelConverterImpl modelConverter) {
        this.modelConverter = modelConverter;
    }

    @Override
    public Loan convert(se.tink.grpc.v1.models.Loan source) {
        Loan loan = new Loan();
        loan.setAccountId(source.getAccountId());
        loan.setNumMonthsBound(modelConverter.map(source.getNumberOfMonthsBound(), Integer.class) );
        loan.setInterest(modelConverter.map(source.getInterest(), ExactNumber.class));
        loan.setType(getType(source.getType()));
        return loan;
    }

    @Override
    public Class<se.tink.grpc.v1.models.Loan> getSourceClass() {
        return se.tink.grpc.v1.models.Loan.class;
    }

    @Override
    public Class<Loan> getDestinationClass() {
        return Loan.class;
    }

    private static Loan.Type getType(se.tink.grpc.v1.models.Loan.Type type) {
        switch (type) {
            case TYPE_BLANCO:
                return Loan.Type.TYPE_BLANCO;
            case TYPE_LAND:
                return Loan.Type.TYPE_LAND;
            case TYPE_MEMBERSHIP:
                return Loan.Type.TYPE_MEMBERSHIP;
            case TYPE_MORTGAGE:
                return Loan.Type.TYPE_MORTGAGE;
            case TYPE_OTHER:
                return Loan.Type.TYPE_OTHER;
            case TYPE_STUDENT:
                return Loan.Type.TYPE_STUDENT;
            case TYPE_VEHICLE:
                return Loan.Type.TYPE_VEHICLE;
            case TYPE_UNKNOWN:
            default:
                return Loan.Type.TYPE_UNKNOWN;
        }
    }
}
