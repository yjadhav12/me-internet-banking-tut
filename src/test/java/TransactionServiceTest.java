import com.group.internet.banking.dao.DataRepository;
import com.group.internet.banking.exception.BusinessException;
import com.group.internet.banking.model.TransactionRequest;
import com.group.internet.banking.model.TransactionResponse;
import com.group.internet.banking.service.TransactionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    private DataRepository dataRepository;

    @Before
    public void init() {
        dataRepository = DataRepository.getInstance();
    }

    @Test
    public void testBalanceWithReversalTrue() throws ParseException, BusinessException {

        TransactionRequest transactionRequest =  new TransactionRequest();
        transactionRequest.setFromAccount("ACC334455");
        transactionRequest.setFromTime(new SimpleDateFormat("dd/MM/yyyy HH:mm:SS").parse("20/10/2018 12:00:00"));
        transactionRequest.setToTime(new SimpleDateFormat("dd/MM/yyyy HH:mm:SS").parse("20/10/2018 19:00:00"));
        transactionRequest.setWithReversal(true);

        TransactionResponse transactionResponse = transactionService.checkBalance(transactionRequest);

        System.out.println("Hello " + transactionResponse.getBalance());
        System.out.println("Hello " + transactionResponse.getNumberOfTxns());

        assertEquals(-25, transactionResponse.getBalance().getNumber().intValue());
        assertEquals(1, transactionResponse.getNumberOfTxns().intValue());
    }

    @Test
    public void testBalanceWithReversalFalse() throws ParseException, BusinessException {

        TransactionRequest transactionRequest =  new TransactionRequest();
        transactionRequest.setFromAccount("ACC334455");
        transactionRequest.setFromTime(new SimpleDateFormat("dd/MM/yyyy HH:mm:SS").parse("20/10/2018 12:00:00"));
        transactionRequest.setToTime(new SimpleDateFormat("dd/MM/yyyy HH:mm:SS").parse("20/10/2018 19:00:00"));
        transactionRequest.setWithReversal(false);

        TransactionResponse transactionResponse = transactionService.checkBalance(transactionRequest);

        System.out.println("Hello " + transactionResponse.getBalance());
        System.out.println("Hello " + transactionResponse.getNumberOfTxns());

        assertEquals(-35.5, transactionResponse.getBalance().getNumber().doubleValueExact(), 1);
        assertEquals(2, transactionResponse.getNumberOfTxns().intValue());
    }

    @Test(expected = BusinessException.class)
    public void testBusinessExceptionForInvalidRelatedTxn() throws ParseException, BusinessException {

        TransactionRequest transactionRequest =  new TransactionRequest();
        transactionRequest.setFromAccount("ACC998877");
        transactionRequest.setFromTime(new SimpleDateFormat("dd/MM/yyyy HH:mm:SS").parse("20/10/2018 12:00:00"));
        transactionRequest.setToTime(new SimpleDateFormat("dd/MM/yyyy HH:mm:SS").parse("20/10/2018 19:00:00"));
        transactionRequest.setWithReversal(false);

        TransactionResponse transactionResponse = transactionService.checkBalance(transactionRequest);

        System.out.println("Hello " + transactionResponse.getBalance());
        System.out.println("Hello " + transactionResponse.getNumberOfTxns());

        assertEquals(-35.5, transactionResponse.getBalance().getNumber().doubleValueExact(), 1);
        assertEquals(2, transactionResponse.getNumberOfTxns().intValue());
    }
}
