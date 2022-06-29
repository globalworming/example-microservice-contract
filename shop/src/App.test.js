import App from './App';
import React from 'react'
import nock from 'nock'
import {render, fireEvent, waitFor, screen} from '@testing-library/react'
import '@testing-library/jest-dom/extend-expect'


test('where low risk payment is confirmed successfully', async () => {
  render(<App/>)
  Given.paymentServiceAnswersOk()
  clickBuy();
  await waitFor(() => successMessage())
  expect(successMessage()).toHaveTextContent('payment successful')
})

const Given = {
  paymentServiceAnswersOk : () => {
    nock("http://localhost")
        .post('/pay', {user: 'Athena', amount: 5.99})
        .once()
        .reply(200, "payment successful");
  }
}

const clickBuy = () => {
  fireEvent.click(screen.getByLabelText("perform low risk request"))
};

const successMessage = () => screen.getByLabelText('message');
