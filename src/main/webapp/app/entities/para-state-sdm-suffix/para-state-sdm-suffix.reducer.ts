import axios from 'axios';
import {
  ICrudSearchAction,
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IParaStateSdmSuffix, defaultValue } from 'app/shared/model/para-state-sdm-suffix.model';

export const ACTION_TYPES = {
  SEARCH_PARASTATES: 'paraState/SEARCH_PARASTATES',
  FETCH_PARASTATE_LIST: 'paraState/FETCH_PARASTATE_LIST',
  FETCH_PARASTATE: 'paraState/FETCH_PARASTATE',
  CREATE_PARASTATE: 'paraState/CREATE_PARASTATE',
  UPDATE_PARASTATE: 'paraState/UPDATE_PARASTATE',
  DELETE_PARASTATE: 'paraState/DELETE_PARASTATE',
  SET_BLOB: 'paraState/SET_BLOB',
  RESET: 'paraState/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IParaStateSdmSuffix>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type ParaStateSdmSuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: ParaStateSdmSuffixState = initialState, action): ParaStateSdmSuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_PARASTATES):
    case REQUEST(ACTION_TYPES.FETCH_PARASTATE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PARASTATE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PARASTATE):
    case REQUEST(ACTION_TYPES.UPDATE_PARASTATE):
    case REQUEST(ACTION_TYPES.DELETE_PARASTATE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_PARASTATES):
    case FAILURE(ACTION_TYPES.FETCH_PARASTATE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PARASTATE):
    case FAILURE(ACTION_TYPES.CREATE_PARASTATE):
    case FAILURE(ACTION_TYPES.UPDATE_PARASTATE):
    case FAILURE(ACTION_TYPES.DELETE_PARASTATE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_PARASTATES):
    case SUCCESS(ACTION_TYPES.FETCH_PARASTATE_LIST):
      const links = parseHeaderForLinks(action.payload.headers.link);
      return {
        ...state,
        links,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links)
      };
    case SUCCESS(ACTION_TYPES.FETCH_PARASTATE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PARASTATE):
    case SUCCESS(ACTION_TYPES.UPDATE_PARASTATE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PARASTATE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.SET_BLOB:
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType
        }
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/para-states';
const apiSearchUrl = 'api/_search/para-states';

// Actions

export const getSearchEntities: ICrudSearchAction<IParaStateSdmSuffix> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_PARASTATES,
  payload: axios.get<IParaStateSdmSuffix>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`)
});

export const getEntities: ICrudGetAllAction<IParaStateSdmSuffix> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_PARASTATE_LIST,
    payload: axios.get<IParaStateSdmSuffix>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IParaStateSdmSuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PARASTATE,
    payload: axios.get<IParaStateSdmSuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IParaStateSdmSuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PARASTATE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IParaStateSdmSuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PARASTATE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IParaStateSdmSuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PARASTATE,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType
  }
});

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
