import App from './App';
import React from 'react'
import nock from 'nock'
import {render, fireEvent, waitFor, screen} from '@testing-library/react'
import '@testing-library/jest-dom/extend-expect'

// TODO refactor to given and steps
function setupSystemUnderTest() {
  render(<App/>)
}

test('where low risk payment is confirmed successfully', async () => {
  setupSystemUnderTest();
  GivenMock.paymentServiceAnswersOk()
  enterName("Athena")
  buyCofee();
  await waitFor(() => successMessage())
  expect(successMessage()).toHaveTextContent('payment successful')
})

test('where high risk payment shows 2fa needed', async () => {
  setupSystemUnderTest();
  GivenMock.paymentServiceAnswersSecondFactorNeeded()
  enterName("Riko")
  buyCar();
  await waitFor(() => twoFactorRequired())
  expect(twoFactorRequired()).toHaveTextContent('2fa required')
})

const GivenMock = {
  paymentServiceAnswersOk : () => {
    nock("http://localhost")
        .post('/pay', {user: 'Athena', amount: 5.99})
        .once()
        .reply(200, "payment successful");
  },

  paymentServiceAnswersSecondFactorNeeded : () => {
    nock("http://localhost")
        .post('/pay', {user: 'Riko', amount: 10000.99})
        .once()
        .reply(400, "2fa required");
  }
}

const buyCofee = () => {
  fireEvent.click(screen.getByLabelText("buy coffee"))
};

const buyCar = () => {
  fireEvent.click(screen.getByLabelText("buy car"))
};

const enterName = (name) => {
  let byLabelText = screen.getByLabelText("user name input");
  fireEvent.change(byLabelText,  {target: {value: name}}  )
};

const successMessage = () => screen.getByLabelText('message');
const twoFactorRequired = () => screen.getByLabelText('error message');
